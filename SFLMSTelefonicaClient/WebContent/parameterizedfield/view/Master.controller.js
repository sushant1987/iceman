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
jQuery.sap.require("SFLMSExtCustomFieldConfig.util.Formatter");
jQuery.sap.require("SFLMSExtCustomFieldConfig.util.Controller");
jQuery.sap.require("sap.m.MessageBox");

SFLMSExtCustomFieldConfig.util.Controller.extend("SFLMSExtCustomFieldConfig.view.Master", {
    
    _oItemTemplate: null,
    

	onInit : function() {	
	    this._oItemTemplate = this.byId("ObjectListItem").clone();
		this.oInitialLoadFinishedDeferred = jQuery.Deferred();

		var oEventBus = this.getEventBus();
		oEventBus.subscribe("Master", "refresh", this.onRefreshParamList, this);
		
		this.getView().byId("fieldList").attachEventOnce("updateFinished", function() {
			this.oInitialLoadFinishedDeferred.resolve();
			this.selectFirstItem();
			oEventBus.publish("Master", "InitialLoadFinished", {
				oListItem : this.getView().byId("fieldList").getItems()[0]
			});
		}, this);

		//on phones, we will not have to select anything in the list so we don't need to attach to events
		if (sap.ui.Device.system.phone) {
			return;
		}

		this.getRouter().attachRoutePatternMatched(this.onRouteMatched, this);

		oEventBus.subscribe("Detail", "Changed", this.onDetailChanged, this);
		oEventBus.subscribe("Detail", "NotFound", this.onNotFound, this);
		
		this._serviceURL = this.getOwnerComponent().getMetadata().getConfig().serviceConfig.serviceUrl;
		
	},

	onRouteMatched : function(oEvent) {
		var sName = oEvent.getParameter("name");

		if (sName !== "main") {
			return;
		}

		//Load the detail view in desktop
		this.getRouter().myNavToWithoutHash({
			currentView : this.getView(),
			targetViewName : "SFLMSExtCustomFieldConfig.view.Detail",
			targetViewType : "XML" 
		});

		//Wait for the list to be loaded once
		this.waitForInitialListLoading(function() {
			//On the empty hash select the first item
//			this.selectFirstItem();
		});

	},
	
	onRefreshParamList: function(sChanel, sEvent, oData){

		var that = this;
		this.getOwnerComponent().loadParamterList(function(){

			if(oData.fieldId){
				var fId = oData.fieldId;


				//set the selection to new created/updated item 
				var oList = that.getView().byId("fieldList");
				var aItems = oList.getItems();
				for ( var i = 0; i < aItems.length; i++) {
					var index = aItems[i].getBindingContext().getPath().match("/\\d+$")[0].replace("/","");
					if(that.getView().getModel().getData()[index].id === fId){
						oList.setSelectedItem(aItems[i], true);
						that.showDetail(aItems[i]);
					}
				}
			}else{
				that.selectFirstItem();
			}
		});

		
	},
	onDetailChanged : function(sChanel, sEvent, oData) {
		var sEntityPath = oData.sEntityPath;
		//Wait for the list to be loaded once
		this.waitForInitialListLoading(function() {
			var oList = this.getView().byId("fieldList");

			var oSelectedItem = oList.getSelectedItem();
			// the correct item is already selected
			if (oSelectedItem && oSelectedItem.getBindingContext().getPath() === sEntityPath) {
				return;
			}
			var aItems = oList.getItems();
			for ( var i = 0; i < aItems.length; i++) {
				if (aItems[i].getBindingContext().getPath() === sEntityPath) {
					oList.setSelectedItem(aItems[i], true);
					break;
				}
			}
		});
	},
	waitForInitialListLoading : function(fnToExecute) {
		jQuery.when(this.oInitialLoadFinishedDeferred).then(jQuery.proxy(fnToExecute, this));
	},

	onNotFound : function() {
		this.getView().byId("fieldList").removeSelections();
	},
	selectFirstItem : function() {
		var oList = this.getView().byId("fieldList");
		var aItems = oList.getItems();

		if (aItems.length && aItems.length > 0) {
			oList.setSelectedItem(aItems[0], true);
			this.showDetail(aItems[0]);
		}else{
			//no parameter
			var bReplace = jQuery.device.is.phone ? false : true;
			this.getRouter().navTo("newDetail", {
				from : "master"
			}, bReplace);
		}
	},

	onSearch : function() {
		// add filter for search
		var filters = [];
		var searchString = this.getView().byId("searchField").getValue();
		if (searchString && searchString.length > 0) {
			filters = [ new sap.ui.model.Filter("Name",
					sap.ui.model.FilterOperator.Contains, searchString) ];
		}

		// update list binding
		this.getView().byId("fieldList").getBinding("items").filter(filters);
	},

	onSelect : function(oEvent) {
		// Get the list item, either from the listItem parameter or from the event's
		// source itself (will depend on the device-dependent mode).
		this.showDetail(oEvent.getParameter("listItem") || oEvent.getSource());
	},
	showDetail : function(oItem) {
		// If we're on a phone, include nav in history; if not, don't.
		var bReplace = jQuery.device.is.phone ? false : true;
		var oJsonData = this.getView().getModel().getData();
		var index = oItem.getBindingContext().getPath().match("/\\d+$")[0].replace("/","");
		var fieldId = oJsonData[index].id;                         
		this.getRouter().navTo("detail", {
			from : "master",
			row:index,
			fieldId : fieldId,
			tab : this.sTab
		}, bReplace);
	},
	onGoHome: function(oEvent) {
		// window.history.go(-1); 
		var url = window.location.origin + "/"+location.pathname.split("/")[1];
        sap.m.URLHelper.redirect(url);
	},
	onExit : function(oEvent){
		var oEventBus = this.getEventBus();
		oEventBus.unsubscribe("Detail", "TabChanged", this.onDetailTabChanged, this);
		oEventBus.unsubscribe("Detail", "Changed", this.onDetailChanged, this);
		oEventBus.unsubscribe("Detail", "NotFound", this.onNotFound, this);
	},
	
	onAddButtonPressed: function(oEvent) {
		
		var bReplace = jQuery.device.is.phone ? false : true;
		this.getRouter().navTo("newDetail", {
			from : "master"
		}, bReplace);
	},
	
	handleFieldDelete: function(oEvent) {
		var oList = this.byId("fieldList");
		var oBinding = oList.getBinding("items");
		var rowNum = oEvent.getParameter("listItem").getBindingContext().getPath().match("/\\d+$")[0].replace("/","");
		var oModel = this.getView().getModel();
		var oItemTemplate = this.byId("ObjectListItem").clone();
		var that = this;
		var requestData = oModel.getData()[rowNum];
		var dialog = new sap.m.Dialog({
			title : "Confirm Delete",
			content : [
				new sap.m.Text({
					text : "Are you sure you want to delete this custom field configuration?"
				}).addStyleClass("stdMarginBottom")
			],
			contentWidth : "30rem",
			stretchOnPhone : true,
			beforeOpen : function () {
				dialog._confirmed = false;
			},
			leftButton : new sap.m.Button({
				text : "Cancel",
				press : function () {
					dialog.close();
				}
			}),
			rightButton : new sap.m.Button({
				text : "Confirm",
				press : function () {
					dialog._confirmed = true;
					dialog.close();
				}
			}),
			afterClose : function (evt) {
				if (dialog._confirmed) {
					// open busy dialog
					var busyDialog = new sap.m.BusyDialog({
						showCancelButton : false,
						title : "Deleting Custom Field Configuration...",
						close : function () {
							jQuery.ajax({
								url:that._serviceURL,
								dataType:"json",
								type:"DELETE",
								contentType:"application/json; charset=utf-8",
								data:JSON.stringify(requestData),
								success:function(data){
									console.debug('success');
									if(data){
										that.getEventBus().publish("Master", "refresh");
									}
								},error:function(resp){
									if(resp.responseText === "success"){
										that.getEventBus().publish("Master", "refresh");
									}
								}
							});
						}
					});
					busyDialog.open();
					
					// close busy dialog after some delay
					setTimeout(jQuery.proxy(function () {
						busyDialog.close();
						busyDialog.destroy();
					}, this), 2000);
				}
				
				// clean up
				dialog.destroy();
			}
		});
		
		// open dialog
		dialog.open();

	},
	
	createBy : function(createBy, createOn){
		return "Created by "+createBy+" on "+this.convertDate(createOn)+"";
	}
	
});