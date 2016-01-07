sap.ui.core.mvc.Controller
	.extend(
		"LMS_ODATA3.view.DetailParticipant", {

	_reportType: null,
	_selectOptions: {},
	_overviewURL: null,
	onInit: function() {
		this.oInitialLoadFinishedDeferred = jQuery.Deferred();

		this.router = sap.ui.core.UIComponent.getRouterFor(this);

		if (sap.ui.Device.system.phone) {
			//Do not wait for the master when in mobile phone resolution
			this.oInitialLoadFinishedDeferred.resolve();
		} else {
			//			this.getView().setBusy(true);
			var oEventBus = this.getEventBus();
			// oEventBus.subscribe("Component", "MetadataFailed", this.onMetadataFailed, this);
			oEventBus.subscribe("Master", "InitialLoadFinished", this.onMasterLoaded, this);
		}
		this.getRouter().getRoute("detailparticipant").attachPatternMatched(this.onRouteMatched, this);
		// this.getRouter().getRoute("detail").attachPatternMatched(this.onRouteMatched, this);
		// this.getRouter().attachRouteMatched(this.onRouteMatched, this);
		this._overviewURL = this.getOwnerComponent().getMetadata().getConfig().serviceConfig.overview;
	},
			onMasterLoaded: function(sChannel, sEvent) {
				this.getView().setBusy(false);
				this.oInitialLoadFinishedDeferred.resolve();
			},

			onMetadataFailed: function() {
				this.getView().setBusy(false);
				this.oInitialLoadFinishedDeferred.resolve();
				this.showEmptyView();
			},

			onRouteMatched: function(oEvent) {
//				var currentReportType = oEvent
//					.getParameter("arguments").reportType;
//				if (!currentReportType) {
//					return;
//				}
//				// refresh the page , only report type is changed
//				if (this._reportType === currentReportType) {
//					return;
//				}
//				this._reportType = currentReportType;
				var genReport = oEvent.getParameter("arguments").genreport;
				if (genReport === "true"){
					this.getView().byId("i_comCode").setValue("");
					this.getView().byId("i_employeeId").setValue("");
					this.onSearch();
				}
				
				var oJsonModel = new sap.ui.model.json.JSONModel();
				oJsonModel.setData(this._selectOptions);
				this.getView().byId("selOptionForm").setModel(
					oJsonModel);

				// clear the overview table
				this.getView().byId("overviewTable").unbindRows();

				// clear the generate result
				this.getView().byId("tablecomponent").destroyContent();
			},

			onSearch: function(oEvent) {
				var opt = this.getView().byId("selOptionForm")
					.getModel().getData();
				var comCode = opt.custom03;
				var dateSel = opt.dateSelection;
				if (dateSel) {
					var from = dateSel.split("-")[0].replace(
						/[(^\s+)(\s+$)]/g, "");
					var to = dateSel.split("-")[1].replace(
						/[(^\s+)(\s+$)]/g, "");
					var df1 = sap.ui.core.format.DateFormat
						.getDateInstance();
					var df2 = sap.ui.core.format.DateFormat
						.getDateInstance({
							pattern: "dd-MM-yyyy",
							style: "medium"
						});
					dateSel = df2.format(df1.parse(from)) + "-" + df2.format(df1.parse(to));
				}
				var empId = opt.employeeId;
				var queryURL = this.getOwnerComponent()
					.setURLParameters(
						this._overviewURL.participant, [comCode, empId, dateSel]);

				var ovTable = this.getView().byId("overviewTable");
				ovTable.setBusy(true);
				var ojsonModel = new sap.ui.model.json.JSONModel(
					queryURL);
				ojsonModel.attachRequestCompleted(function() {
					ovTable.setBusy(false);
				});
				ovTable.setModel(ojsonModel);
				this.getView().byId("overviewTable").bindRows({
					path: "/"
				});
			},

			showEmptyView: function() {
				this.getRouter().myNavToWithoutHash({
					currentView: this.getView(),
					targetViewName: "LMS_ODATA3.view.NotFound",
					targetViewType: "XML"
				});
			},

			fireDetailNotFound: function() {
				this.getEventBus().publish("Detail", "NotFound");
			},

			onNavBack: function() {
				// This is only relevant when running on phone devices
				this.getRouter().myNavBack("main");
			},

			onDetailSelect: function(oEvent) {
				sap.ui.core.UIComponent.getRouterFor(this).navTo(
					"detail", {
						entity: oEvent.getSource()
							.getBindingContext().getPath()
							.slice(1),
						tab: oEvent.getParameter("selectedKey")
					}, true);
			},

			openActionSheet: function() {

				if (!this._oActionSheet) {
					this._oActionSheet = new sap.m.ActionSheet({
						buttons: new sap.ushell.ui.footerbar.AddBookmarkButton()
					});
					this._oActionSheet.setShowCancelButton(true);
					this._oActionSheet
						.setPlacement(sap.m.PlacementType.Top);
				}

				this._oActionSheet.openBy(this.getView().byId(
					"actionButton"));
			},

			getEventBus: function() {
				return sap.ui.getCore().getEventBus();
			},

			getRouter: function() {
				return sap.ui.core.UIComponent.getRouterFor(this);
			},

			onExit: function(oEvent) {
				var oEventBus = this.getEventBus();
				oEventBus.unsubscribe("Master", "InitialLoadFinished",
					this.onMasterLoaded, this);
				oEventBus.unsubscribe("Component", "MetadataFailed",
					this.onMetadataFailed, this);
				if (this._oActionSheet) {
					this._oActionSheet.destroy();
					this._oActionSheet = null;
				}
			},

			onDisplayRecord: function(oEvent) {

				// get overview table selection
				var overviewTable = this.getView()
					.byId("overviewTable");
				var selectedIndex = overviewTable.getSelectedIndex();
				var selectedPath = overviewTable.getRows()[selectedIndex]
					.getBindingContext().sPath;
				var rowNo = selectedPath.match("/\\d+$")[0].replace(
					"/", "");
				var comCode = overviewTable.getModel().getData()[rowNo].legalEntity;
				var opt = this.getView().byId("selOptionForm")
					.getModel().getData();

				if (opt.empId) {
					var empId = opt.empId;
				} else {
					empId = "none";
				}

				if (opt.dateSelection) {
					var dateSelection = opt.dateSelection;
				} else {
					dateSelection = "none";
				}

				this.getRouter().navTo("participants", {
					comCode: comCode,
					query: {
						empId: empId,
						dateSel: dateSelection,
						runId: "none"
					}
				});

			},

			pressListMethod: function() {
				// var oSelectedKey =
				// sap.ui.getCore().byId("keyid").getSelectedItem().getKey();
				this.getOwnerComponent().getRouter().navTo("items");
			}
		});