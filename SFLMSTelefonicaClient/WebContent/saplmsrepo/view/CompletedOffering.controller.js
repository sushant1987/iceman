jQuery.sap.require("sap.ui.core.format.DateFormat");
sap.ui.core.mvc.Controller.extend("LMS_ODATA3.view.CompletedOffering", {
	_queryInfo: {},
	onInit: function() {
		this.getRouter().getRoute("offeringcomp").attachPatternMatched(this.onRouteMatched, this);
	},

	onNavBack: function() {
		// This is only relevant when running on phone devices
		// this.getRouter().myNavBack("detailPage");
		window.history.go(-1);
	},

	onRouteMatched: function(oEvent) {
		var oJsonModel = new sap.ui.model.json.JSONModel();
		var itemServiceURL = this.getOwnerComponent().getMetadata().getConfig().serviceConfig.completeOfferingServiceUrl;
		oJsonModel.loadData(itemServiceURL);

		this.getView().setModel(oJsonModel);

		var legalEntity = oEvent.getParameter("arguments").LegalEntity;
		var query = oEvent.getParameter("arguments")['?query'];

		//save the queryinfo for later download
		this._queryInfo = {
			legalEntity: legalEntity,
			offerId: query.offerId,
			dateSel: query.dateSel
		};

		if (!legalEntity) {
			return;
		}
		var oFilter = new sap.ui.model.Filter("LegalEntity", sap.ui.model.FilterOperator.EQ, legalEntity);
		var oTables = [this.getView().byId("Scheduledofferingset"),
			this.getView().byId("ParitipantDetails"),
			this.getView().byId("ParticipantPersonal"),
			this.getView().byId("ProfessionalContribution")
		];
		for (var i = 0; i < oTables.length; i++) {
			oTables[i].bindRows({
				path: "/",
				filters: [oFilter]
			});
		}
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
		var fileURL = this.getOwnerComponent().setURLParameters("/SFLMSTelefonicaClient/a/CompletedOfferingReport/{legalEntity}/{id}/{date}", [
			this._queryInfo.legalEntity, this._queryInfo.offerId, this._queryInfo.dateSel
		]);
		var link = document.createElement("a");
		link.download = "CompletedOffering.xml";
		link.href = fileURL;
		link.click();
	}
});