jQuery.sap.require("sap.ui.core.mvc.Controller");
jQuery.sap.require("sap.ui.table.SortOrder");
jQuery.sap.require("sap.ui.model.Sorter"); 

jQuery.sap.require("sap.ui.core.format.DateFormat");
sap.ui.core.mvc.Controller.extend("LMS_ODATA3.view.ScheduledOffering", {
	_queryInfo: {},
	onInit: function() {
		this.getRouter().getRoute("offeringnew").attachPatternMatched(this.onRouteMatched, this);
	},

	onNavBack: function() {
		// This is only relevant when running on phone devices
		// this.getRouter().myNavBack("detailPage");
		window.history.go(-1);
	},

	onRouteMatched: function(oEvent) {
		var oJsonModel = new sap.ui.model.json.JSONModel();
		var legalEntity = oEvent.getParameter("arguments").legalEntity;
		var query = oEvent.getParameter("arguments")['?query'];
		
		//save the queryinfo for later download
		this._queryInfo = {
			legalEntity: legalEntity,
			offerId: query.offerId,
			nodays: query.nodays,
			dateSel: query.dateSel,
			runid: query.runId
		};

		var newOfferingServiceUrl = this.getOwnerComponent().getMetadata().getConfig().serviceConfig.newOfferingServiceUrl;

		newOfferingServiceUrl = this.getOwnerComponent().setURLParameters(newOfferingServiceUrl, [this._queryInfo.legalEntity,
			this._queryInfo.offerId, this._queryInfo.nodays,this._queryInfo.dateSel, this._queryInfo.runid
		]);

		oJsonModel.loadData(newOfferingServiceUrl);
		
		var oTables = this.getView().byId("ScheduledOffering");
		 oTables.setBusy(true);
//		var ojsonModel = new sap.ui.model.json.JSONModel(
//				queryURL);
		oJsonModel.attachRequestCompleted(function() {
			oTables.setBusy(false);
		});

		this.getView().setModel(oJsonModel);

		if (!legalEntity) {
			return;
		}
		// var oFilter = new sap.ui.model.Filter("LegalEntity", sap.ui.model.FilterOperator.EQ, legalEntity);
		// var oTables = [this.getView().byId("ScheduledOffering"),
		// 	this.getView().byId("Facility"),
		// 	this.getView().byId("Duration"),
		// 	this.getView().byId("OnlinePlatform"),
		// 	this.getView().byId("OnlineDuartion"),
		// 	this.getView().byId("Instructor")
		// ];
		// for (var i = 0; i < oTables.length; i++) {
		// 	oTables[i].bindRows({
		// 		path: "/",
		// 		filters: [oFilter]
		// 	});
		// }
	},
	handleSelectionChange: function(oEvent){
		
		var rowsel = oEvent.getParameter("rowContext");
		var newBindPath = rowsel.sPath+"/instructors";
		
		var oTables = this.getView().byId("instsecondary");
		
		oTables.bindRows({
				path: newBindPath
			});

	},
	getRouter: function() {
		return sap.ui.core.UIComponent.getRouterFor(this);
	},
	formatDate: function(val) {
		if (val) {
			var tagetFormat = sap.ui.core.format.DateFormat.getDateInstance("MMM d,y");
			var sddate = new Date(parseFloat(val.match("\\d+")[0]));
			if (sddate) {
				var sDateString = tagetFormat.format(sddate);
				return sDateString;
			} else {
				return val;
			}
		}
		return val;
	},
	onPress: function() {
		var fileURL = this.getOwnerComponent().setURLParameters("/SFLMSTelefonicaClient/a/NewOfferingReport/{legalEntity}/{id}/none/{date}/{runid}", [
			this._queryInfo.legalEntity, this._queryInfo.offerId, this._queryInfo.dateSel, this._queryInfo.runid
		]);
		var link = document.createElement("a");
		link.download = "NewOffering.xml";
		link.href = fileURL;
		link.click();
		var newoff = "New Offerings";
		this.getRouter().navTo("detail", {reportType : newoff , genreport : true});
	}
});