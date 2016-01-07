sap.ui.core.mvc.Controller.extend("LMS_ODATA3.view.Master", {

	onInit: function() {
		this.oInitialLoadFinishedDeferred = jQuery.Deferred();

		var oEventBus = this.getEventBus();
		oEventBus.subscribe("Detail", "TabChanged", this.onDetailTabChanged, this);

		var i18nModel = new sap.ui.model.resource.ResourceModel({
			bundleUrl: "i18n/messageBundle.properties"
		});
		sap.ui.getCore().setModel(i18nModel, "i18n");

		this._initViewPropertiesModel(i18nModel);

		//On phone devices, there is nothing to select from the list. There is no need to attach events.
		if (sap.ui.Device.system.phone) {
			return;
		}

		this.getRouter().attachRoutePatternMatched(this.onRouteMatched, this);
		
		oEventBus.subscribe("Detail", "NotFound", this.onNotFound, this);
	},

	_initViewPropertiesModel: function(oi18nModel) {
		var oViewElemProperties = {};
		oViewElemProperties.rollbackTitleText = oi18nModel.getProperty("rollbackTitleText");
		oViewElemProperties.archiveTitleText = oi18nModel.getProperty("archiveTitleText");
		oViewElemProperties.backupTitleText = oi18nModel.getProperty("backupTitleText");
		oViewElemProperties.restoreTitleText = oi18nModel.getProperty("restoreTitleText");
		oViewElemProperties.alertTitleText = oi18nModel.getProperty("alertTitleText");
		if (sap.ui.Device.system.phone) {
			oViewElemProperties.btnColHeaderVisible = true;
			oViewElemProperties.catalogTitleVisible = false;
		} else {
			oViewElemProperties.btnColHeaderVisible = false;
			oViewElemProperties.catalogTitleVisible = true;
		}
		this.oViewProperties = new sap.ui.model.json.JSONModel(oViewElemProperties);
		this.oView.setModel(this.oViewProperties, "viewProperties");
	},

	onRouteMatched: function(oEvent) {
		var sName = oEvent.getParameter("name");

		if (sName === "main") {
			if (!sap.ui.Device.system.phone) {
				//On the empty hash select the first item
				this.selectFirstItem();
			}
		}
		
		if(sName === "detail"){
			
			//select the right item
			var selReportType = oEvent.getParameter("arguments").reportType;
			
			var oList = this.getView().byId("list");
			var aItems = oList.getItems();
			var isValid = false;
			for (var i = 0; i < aItems.length; i++) {
				if (aItems[i].getTitle() === selReportType) {
					oList.setSelectedItem(aItems[i], true);
					isValid = true;
					break;
				}
			}
			
			if(!isValid){
				this.onNotFound();
			}
		}
	},

	onDetailTabChanged: function(sChanel, sEvent, oData) {
		this.sTab = oData.sTabKey;
	},
	
	loadDetailView: function() {
		this.getRouter().getTargets().display("detail");
	},

	onNotFound: function() {
		this.getView().byId("list").removeSelections();
		this.getRouter().getTargets().display("NotFound");
	},

	selectFirstItem: function() {
		var oList = this.getView().byId("list");
		var aItems = oList.getItems();
		if (aItems.length) {
			oList.setSelectedItem(aItems[0], true);
			//Load the detail view in desktop
			this.loadDetailView();
			oList.fireSelect({
				"listItem": aItems[0]
			});
		} else {
			this.getRouter().getTargets().display("NotFound");
		}
	},

	onSelect: function(oEvent) {
		var selecteditem = oEvent.mParameters.listItem.mProperties.title;
	if ( selecteditem === "Offering Status" ) 
	{
		this.showDetailerrlog(oEvent.getParameter("listItem") || oEvent.getSource());
	} 
	else if (selecteditem === "Report History")
	{
		this.showDetailRunID(oEvent.getParameter("listItem") || oEvent.getSource());
	}
	else if (selecteditem === "Participants")
	{
		this.showDetailParticipants(oEvent.getParameter("listItem") || oEvent.getSource());
	}
	else
	{
		this.showDetail(oEvent.getParameter("listItem") || oEvent.getSource());
	}
	},
	
	onSelectOffering: function(oEvent) {
		this.showDetailerrlog(oEvent.getParameter("listItem") || oEvent.getSource());
			// this.getOwnerComponent().getRouter().navTo("detailPageoff");
	},
	
	onSelectRunID: function(oEvent) {
		this.showDetailRunID(oEvent.getParameter("listItem") || oEvent.getSource());
			// this.getOwnerComponent().getRouter().navTo("runidinfo");
	},
	
	// onGoHome: function() {
	// 	sap.m.MessageToast.show("Going Home");
	// },

	showDetail: function(oItem) {
		// If we're on a phone device, include nav in history
		// var bReplace = jQuery.device.is.phone ? false : true;
		this.getRouter().navTo("detail", {
			reportType: oItem.getTitle(),
			tab: this.sTab
		}, true);
	},
	showDetailerrlog: function(oItem) {
		// If we're on a phone device, include nav in history
		// var bReplace = jQuery.device.is.phone ? false : true;
		this.getRouter().navTo("errorlog", {
			from: "master",
			entity: oItem.getId(),
			tab: this.sTab
		}, true);
	},
	showDetailRunID: function(oItem) {
		// If we're on a phone device, include nav in history
		// var bReplace = jQuery.device.is.phone ? false : true;
		this.getRouter().navTo("runidinfo", {
			from: "master",
			entity: oItem.getId(),
			tab: this.sTab
		}, true);
	},
	showDetailParticipants: function(oItem) {
		// If we're on a phone device, include nav in history
		// var bReplace = jQuery.device.is.phone ? false : true;
		this.getRouter().navTo("detailparticipant", {
			genreport: null,
//			from: "master",
//			entity: oItem.getId(),
			tab: this.sTab
		}, true);
	},
	getEventBus: function() {
		return sap.ui.getCore().getEventBus();
	},

	getRouter: function() {
		return sap.ui.core.UIComponent.getRouterFor(this);
	},

	onExit: function(oEvent) {

	},
	onGoHome: function(oEvent) {
		var url = window.location.origin + "/"+location.pathname.split("/")[1];
        sap.m.URLHelper.redirect(url); 
/*		window.history.go(-1); 
*/	}
});