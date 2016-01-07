jQuery.sap.require("sap.ui.core.format.DateFormat");
sap.ui.core.mvc.Controller.extend("LMS_ODATA3.view.Participants", {
	_queryInfo: {},
	onInit: function() {
		this.getRouter().getRoute("participants").attachPatternMatched(this.onRouteMatched, this);
	},

	onNavBack: function() {
		// This is only relevant when running on phone devices
		// this.getRouter().myNavBack("detailPage");
		window.history.go(-1);
	},

	onRouteMatched: function(oEvent) {
        var oJsonModel = new sap.ui.model.json.JSONModel();
        var comCode = oEvent.getParameter("arguments").comCode;
        var query = oEvent.getParameter("arguments")['?query'];
        
		//save the queryinfo for later download
		this._queryInfo = {
			comCode: comCode,
			empId: query.empId,
			dateSel: query.dateSel,
			runid: query.runId
		};
        var participantsServiceUrl = this.getOwnerComponent().getMetadata().getConfig().serviceConfig.participantsServiceUrl;
        
        participantsServiceUrl = this.getOwnerComponent().setURLParameters(participantsServiceUrl, [this._queryInfo.comCode, 
        this._queryInfo.empId, this._queryInfo.dateSel,this._queryInfo.runid ]);
        
        oJsonModel.loadData(participantsServiceUrl);
        
        var oTables = this.getView().byId("ParticipantSet");
		 oTables.setBusy(true);
//		var ojsonModel = new sap.ui.model.json.JSONModel(
//				queryURL);
		oJsonModel.attachRequestCompleted(function() {
			oTables.setBusy(false);
		});
		
        this.getView().setModel(oJsonModel);

//		if (!legalEntity) {
//			return;
//		}		
//		var oFilter = new sap.ui.model.Filter("LegalEntity", sap.ui.model.FilterOperator.EQ, legalEntity);
//		this.getView().byId("ParticipantSet").bindRows({
//			path: "/",
//			filters: [oFilter]
//		});
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
		var fileURL = this.getOwnerComponent().setURLParameters("/SFLMSTelefonicaClient/a/ParticipantsReport/{comCode}/{id}/{date}/{runid}", [
			this._queryInfo.comCode, this._queryInfo.empId, this._queryInfo.dateSel, this._queryInfo.runid
		]);
		var link = document.createElement("a");
		link.download = "Participants.xml";
		link.href = fileURL;
		link.click();
		
		this.getRouter().navTo("detailparticipant", {genreport : true});
	}
});