/**
 * DISCLAIMER:
 * 
 * This view is implemented under the following assumptions related to the selected OData service:
 * 
 * 1. Status Property
 *    a. The main OData collection contains a status property
 *    b. The status property accepts the following values: "New", "In Process", "Approved", and “Rejected".
 *
 * 2. Attachments Collection
 *    a. The OData service provides an attachments collection.
 *    b. There is a navigation property from the main data collection to the attachment collection.
 *    c. Each attachment contains the following properties: file name, content (as a Base-64 string), content type and a unique id.
 *    d. Additionally, each attachment entry contains the id of the item in the main collection that relates to it (foreign key).
 *    e. Only image file attachments are supported.
 *
 * 3. Create Attachment
 *    a. The create operation is executed on the attachments collection.
 *    b. The new attachment entry includes all the attributes mentioned above.
 *    c. The new attachment entry includes the id of the item in the main collection it relates to.
 *    d. The new attachment unique id is created by the service during the create operation.
 * 
 */
jQuery.sap.require("SFLMSExtCustomFieldConfig.util.GuidGenerator");
jQuery.sap.require("SFLMSExtCustomFieldConfig.util.Formatter");
jQuery.sap.require("SFLMSExtCustomFieldConfig.util.Controller");

SFLMSExtCustomFieldConfig.util.Controller.extend("SFLMSExtCustomFieldConfig.view.Detail", {
    
    _oItemTemplate: null,
    _oItemVHTemplate: null,
    _rowNum:null,
    _fieldId:null,
    _isAddingNewParam:false,
    _oldStartDate:null,

	onInit : function() {
	    // this._oItemTemplate = this.byId("columnListItem").clone();
	    // this._oItemVHTemplate = this.byId("VHColumnListItem").clone();
		this.oInitialLoadFinishedDeferred = jQuery.Deferred();

		if (sap.ui.Device.system.phone) {
			//don't wait for the master on a phone
			this.oInitialLoadFinishedDeferred.resolve();
		} else {
//			this.getView().setBusy(true);
			this.getEventBus().subscribe("Master", "InitialLoadFinished", this.onMasterLoaded, this);
		}

		this.getRouter().attachRouteMatched(this.onRouteMatched, this);

	},

	onMasterLoaded : function(sChannel, sEvent, oData) {
		if (oData.oListItem) {
			this.bindView(oData.oListItem.getBindingContext().getPath());
			this.getView().setBusy(false);
			this.oInitialLoadFinishedDeferred.resolve();
		}else{
			this.getView().setBusy(false);
		}
	},
	

	onRouteMatched : function(oEvent) {
		
		if (oEvent.getParameter("name") === "main") {
			return;
		}
		
		if(location.hash === "#/new_detail"){
			//new entry
			this.getView().byId("dpEndDate").setEditable(true);
			
			var now = new Date();
			var defaultEndDate = new Date("9999-12-31");
			var newEntry = {
	        	"paramName": "",
	        	"startDate": this._formatDate(now),
	        	"endDate": this._formatDate(defaultEndDate),
	        	"paramValue": "",
	        	"paramType": "",
	        	"createdByUser": "New User",
	        	"createdDate": this._formatDate(now),
	        	"modifiedByUser": "New User",
	        	"modifiedOn": this._formatDate(now)
			};
			var oJsonModel = new sap.ui.model.json.JSONModel();
			oJsonModel.setData(newEntry);
			this.getView().setModel(oJsonModel);
			this.bindView("/");
			
			this._isAddingNewParam = true;
			this._oldStartDate = newEntry.startDate;
		}else{
			var oParameters = oEvent.getParameters();
			this._rowNum = oParameters.arguments.row;
			this._fieldId = oParameters.arguments.fieldId;
			var that = this;
			jQuery.when(this.oInitialLoadFinishedDeferred).then(jQuery.proxy(function() {
	
				//bind the detail
				//use the component model
				that.getView().setModel(null);
				var sEntityPath = "/" + that._rowNum;
				that.bindView(sEntityPath);
				
				that._oldStartDate = that.getView().getModel().getData()[that._rowNum].startDate;
				// Which tab?
				var sTabKey = oParameters.arguments.tab;
				this.getEventBus().publish("Detail", "TabChanged", {
					sTabKey : sTabKey
				});
			}, this));
			
			this._isAddingNewParam = false;
			this.getView().byId("dpEndDate").setEditable(false);
		}
	},
	
	_formatDate: function(date){
		if(!date){
			return "";
		}
		return date.toISOString().substring(0,10);
	},

	bindView : function(sEntityPath) {

		var oView = this.getView();
		oView.bindElement(sEntityPath);
		
		var that = this;
		this.oModel = oView.getModel();
		this.sEntityPath = sEntityPath;

		//Check if the data is already on the client
		if (!oView.getModel().getData(sEntityPath)) {

			// Check that the entity specified actually was found.
			oView.getElementBinding().attachEventOnce("dataReceived", jQuery.proxy(function() {
				var oData = oView.getModel().getData(sEntityPath);
				if (!oData) {
					this.showEmptyView();
					this.fireDetailNotFound();
				} else {
					this.fireDetailChanged(sEntityPath);
				}
			}, this));

		} else {
			this.fireDetailChanged(sEntityPath);
		}
	},

	showEmptyView : function() {
		this.getRouter().myNavToWithoutHash({
			currentView : this.getView(),
			targetViewName : "SFLMSExtCustomFieldConfig.view.NotFound",
			targetViewType : "XML"
		});
	},

	fireDetailChanged : function(sEntityPath) {
		this.getEventBus().publish("Detail", "Changed", {
			sEntityPath : sEntityPath
		});
	},

	fireDetailNotFound : function() {
		this.getEventBus().publish("Detail", "NotFound");
	},
	
	fireRefreshParameter: function(params){
		this.getEventBus().publish("Master", "refresh", params);
	},
	onNavBack : function() {
		// This is only relevant when running on phone devices
		this.getRouter().myNavBack("main");
	},

	updateModel : function(oEvent) {
		var requestData = null;
		if(this._isAddingNewParam){
			requestData = this.getView().getModel().getData();
		}else{
			requestData = this.getView().getModel().getData()[this._rowNum];
		}
		
		requestData.endDate = this.getView().byId("dpEndDate").getValue();
		requestData.startDate = this.getView().byId("dpBegDate").getValue();
		requestData.modifiedOn = this._formatDate(new Date());
//		requestData.paramType = oEvent.getParameter("selectedItem").getKey();
//		requestData.paramType = this.getView().byId("parType").getSelectedKey();
		requestData.paramType = this.getView().byId("parType").getSelectedItem().getText();
		
		var msg = this._validateFields(requestData);
		if(msg){
			this.showPopup(msg);
			return;
		}
		var that = this;
		var oBusyDialog = new sap.m.BusyDialog();
		oBusyDialog.open();

		var serviceURL = this.getOwnerComponent().getMetadata().getConfig().serviceConfig.serviceUrl;
		jQuery.ajax({
			url:serviceURL,
			dataType:"json",
			type:"POST",
			contentType:"application/json; charset=utf-8",
			data:JSON.stringify(requestData),
			success:function(data){
				oBusyDialog.close();
				
				//refresh the master page
				that.fireRefreshParameter({
					fieldId: data.id
				});
			},error:function(){
				oBusyDialog.close();
			}
		});
	},
	
	_getLongDateValueFromJSONFormat: function(val){
		if(val){
			return new Date(val).getTime();
		}
		return 0;
	},
	
	_validateFields: function(data, oldStartDate){
		if(!data.startDate){
			return "Start date is required";
		}
		if(!data.endDate){
			return "End date is required";
		}
		if(!this._isAddingNewParam){
			if(this._getLongDateValueFromJSONFormat(data.startDate) < this._getLongDateValueFromJSONFormat(this._oldStartDate)){
				return "New start date should not earlier than current start date";
			}
		}
		
		if(this._getLongDateValueFromJSONFormat(data.startDate) > this._getLongDateValueFromJSONFormat(data.endDate)){
			return "Start date should not bigger than end date";
		}
	},
	onSaveSelect : function(oEvent) {
		if(!this._isAddingNewParam){
			this.oCurrentItemData = this.getView().getBindingContext().getObject();
			this.oCurrentItemData.Name = this.byId("textAttributeInputField").getValue();
		}
		this.updateModel(oEvent);
	},

	showPopup : function(message) {
		jQuery.sap.require("sap.m.MessageToast");
		sap.m.MessageToast.show(message);
	},

	onDetailSelect : function(oEvent) {
		sap.ui.core.UIComponent.getRouterFor(this).navTo("detail", {
			entity : oEvent.getSource().getBindingContext().getPath().slice(1),
			tab : oEvent.getParameter("selectedKey")
		}, true);
	},
	onExit : function(oEvent){
		this.getEventBus().unsubscribe("Master", "InitialLoadFinished", this.onMasterLoaded, this);
		if (this._oPopover) {
			this._oPopover.destroy();
		}
	},
	
	handlePopoverPress: function(oEvent) {
		var sPath = oEvent.getSource().getBindingContext().getPath();
		if (!this._oPopover) {
			this._oPopover = sap.ui.xmlfragment("SFLMSExtCustomFieldConfig.view.Popover", this);
			//this._oPopover.bindElement(sPath);
			this.getView().addDependent(this._oPopover);
		}

		this._oPopover.bindElement(sPath);

		this._oPopover.openBy(oEvent.getSource());
	},

	handleCloseButton: function(oEvent) {
		this._oPopover.close();

	},
	
	handleIconTabBarSelect: function(oEvent) {
		sKey = oEvent.getParameter("selectedKey");
		if (sKey === "ValueHelp") {
		    var valueHelpExists = this.byId("hasValueHelp").getState();
		    
		    var oViewElemProperties = {};
		    if(valueHelpExists)
		    {
		        oViewElemProperties.ValueHelpVisible = true;
		    }
		    else
		    {
		        oViewElemProperties.ValueHelpVisible = false;
		    }
		
    		var oViewProperties = new sap.ui.model.json.JSONModel(oViewElemProperties);
    		this.getView().setModel(oViewProperties, "viewProperties");
		    
        }
	}
});