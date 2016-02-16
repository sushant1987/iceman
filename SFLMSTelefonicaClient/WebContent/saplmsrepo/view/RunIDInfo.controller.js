

jQuery.sap.require("sap.ui.core.mvc.Controller");
jQuery.sap.require("sap.ui.table.SortOrder");
jQuery.sap.require("sap.ui.model.Sorter"); 
jQuery.sap.require("sap.ui.core.format.DateFormat");
sap.ui.core.mvc.Controller
		.extend(
				"LMS_ODATA3.view.RunIDInfo",
				{

					_reportType : null,
					_selectOptions : {},
					_overviewURL : null,
					onInit : function() {
						this.oInitialLoadFinishedDeferred = jQuery.Deferred();

						this.router = sap.ui.core.UIComponent
								.getRouterFor(this);

						if (sap.ui.Device.system.phone) {
							// Do not wait for the master when in mobile phone
							// resolution
							this.oInitialLoadFinishedDeferred.resolve();
						} else {
							// this.getView().setBusy(true);
							var oEventBus = this.getEventBus();
							// oEventBus.subscribe("Component",
							// "MetadataFailed", this.onMetadataFailed, this);
							oEventBus.subscribe("Master",
									"InitialLoadFinished", this.onMasterLoaded,
									this);
						}
						this
								.getRouter()
								.getRoute("runidinfo")
								.attachPatternMatched(this.onRouteMatched, this);
						// this.getRouter().getRoute("detail").attachPatternMatched(this.onRouteMatched,
						// this);
						// this.getRouter().attachRouteMatched(this.onRouteMatched,
						// this);
						this._overviewURL = this.getOwnerComponent()
								.getMetadata().getConfig().serviceConfig.overview;
					},
					onNavOfferring : function(oEvent) {

//						var context = oEvent.getParameters().srcControl;
//						var overviewTableModel = oEvent.getSource().getModel();

//						var overviewTable = this.getView().byId("overviewTable");
//						var selectedIndex = overviewTable.getSelectedIndex();
//						var selectedPath = overviewTable.getRows()[selectedIndex]
//								.getBindingContext().sPath;
//						var rowNo = selectedPath.match("/\\d+$")[0].replace(
//								"/", "");
//						var legalEntity = overviewTable.getModel().getData()[rowNo].legalEntity;
//						var xmltype     = overviewTable.getModel().getData()[rowNo].reportType;
//						var runid       = overviewTable.getModel().getData()[rowNo].id;

//						var xmltype = overviewTableModel.getProperty(context
//								.getBindingContextPath()).reportType;
//						var legalEntity = overviewTableModel
//								.getProperty(context.getBindingContextPath()).legalEntity;
//						var runid = overviewTableModel.getProperty(context
//								.getBindingContextPath()).id;
						// var date =
						// overviewTableModel.getProperty(context.getBindingContextPath()).createdDate;
						
						var overviewTable = this.getView().byId("overviewTable");
						var selectedIndex = overviewTable.getSelectedIndex();
						var selectedPath  = overviewTable.getModel().getData()[selectedIndex];
						var legalEntity   = selectedPath.legalEntity;
						var xmltype       = selectedPath.reportType;
						var runid 		  = selectedPath.id;
						 
						if (legalEntity) {
							var LE = legalEntity;
						} else {
							LE = "none";
						}
						var self = this;
						if (xmltype === "Item") {
							self.router.navTo("items", {
								legalEntity : LE,
								query : {
									itemId : "none",
									dateSel : "none",
									runId : runid
								}
							});
						} else if (xmltype === "New Offering") {
							self.router.navTo("offeringnew", {
								legalEntity : LE,
								query : {
									offerId : "none",
									dateSel : "none",
									runId : runid
								}
							});
						} else if (xmltype === "Completed Offering") {
							self.router.navTo("offeringcomp", {
								legalEntity : LE,
								query : {
									offerId : "none",
									dateSel : "none",
									runId : runid
								}
							});
						} else if (xmltype === "Participants") {
							self.router.navTo("participants", {
								legalEntity : LE,
								query : {
									empId : "none",
									dateSel : "none",
									runId : runid
								}
							});
						}

						// var context = this.getBindingContext();
						// var overviewTable =
						// this.getView().byId("overviewTable");
						// var context = oEvent.getSource().getBindingContext();
						// var context =
						// oEvent.oControlEvent.getParameters.srcControl;
						// var runid = context.getProperty("RunId");
						// var xmltype = context.getProperty("XMLType");

						// var overviewTable =
						// this.getView().byId("overviewTable");
						// var selectedIndex = overviewTable.getSelectedIndex();
						// var selectedPath =
						// overviewTable.getRows()[selectedIndex].getBindingContext().sPath;
						// var rowNo =
						// selectedPath.match("/\\d+$")[0].replace("/", "");
						// var legalEntity =
						// overviewTable.getModel().getData()[rowNo].legalEntity;
						// var opt =
						// this.getView().byId("selOptionForm").getModel().getData();
					},

					onSearch : function() {
						var opt = this.getView().byId("selOptionForm")
								.getModel().getData();
						var legalEntity = opt.legalEntity;
						var runid = opt.id;
						var dateSel = opt.date;
						if (dateSel) {
							var from = dateSel.split("-")[0].replace(
									/[(^\s+)(\s+$)]/g, "");
							var to = dateSel.split("-")[1].replace(
									/[(^\s+)(\s+$)]/g, "");
							var df1 = sap.ui.core.format.DateFormat
									.getDateInstance();
							var df2 = sap.ui.core.format.DateFormat
									.getDateInstance({
										pattern : "dd-MM-yyyy",
										style : "medium"
									});
							dateSel = df2.format(df1.parse(from)) + "-"
									+ df2.format(df1.parse(to));
						}

						var queryURL = this.getOwnerComponent()
								.setURLParameters(
										this._overviewURL.reportHistory,
										[ legalEntity, runid, dateSel ]);
//						var queryURL = "model/RunIdInfoSet.json";
						var ovTable = this.getView().byId("overviewTable");
						ovTable.setBusy(true);
						var ojsonModel = new sap.ui.model.json.JSONModel(
								queryURL);
						ojsonModel.attachRequestCompleted(function() {
							ovTable.setBusy(false);
						});
						ovTable.setModel(ojsonModel);
						this.getView().byId("overviewTable").bindRows({
							path : "/"
						}); 
//						 var oTable = this.getView().byId("overviewTable");
//						 oTable.bindItems({
//						 path:"/"
//						 });
//						 this.getView().byId("overviewTable").bindItems({
//						 path: "/"
//						 });
//						 this.getView().byId("overviewTable").bindRows({
//						 path: "/"
//						 });
					},
					
//					sortRunId : function(oEvent){
//						var oView = this.getView();
//						var oTable = oView.byId("overviewTable");
//						oTable.sort(oView.byId("id"), SortOrder.Ascending, false);
////						oTable.sort(oView.byId("name"), SortOrder.Ascending, true);
//					},
					
					onMasterLoaded : function(sChannel, sEvent) {
						this.getView().setBusy(false);
						this.oInitialLoadFinishedDeferred.resolve();
					},

					onMetadataFailed : function() {
						this.getView().setBusy(false);
						this.oInitialLoadFinishedDeferred.resolve();
						this.showEmptyView();
					},

					onRouteMatched : function(oEvent) {
						var oJsonModel = new sap.ui.model.json.JSONModel();
						oJsonModel.setData(this._selectOptions);
						this.getView().byId("selOptionForm").setModel(
								oJsonModel);

						// clear the overview table
						// this.getView().byId("overviewTable").unbindRows();

						// clear the generate result
						// this.getView().byId("tablecomponent").destroyContent();
						// var oParameters = oEvent.getParameters();
						// // this.oInitialLoadFinishedDeferred =
						// jQuery.Deferred();
						// // var paneltab = 'tablecomponent_panel';
						// // var paneldata = this.byId(paneltab);
						// // paneldata.destroyLayoutData();

						// var oJsonModel = new sap.ui.model.json.JSONModel();
						// oJsonModel.setData(this._selectOptions);
						// this.getView().byId("selOptionForm").setModel(oJsonModel);

						// //clear the overview table
						// this.getView().byId("overviewTable").unbindRows();

						// //clear the generate result
						// this.getView().byId("tablecomponent").destroyContent();

						// jQuery.when(this.oInitialLoadFinishedDeferred).then(jQuery.proxy(function()
						// {
						// var oView = this.getView();

						// // When navigating in the Detail page, update the
						// binding context
						// if (oParameters.name !== "detail") {
						// return;
						// }

						// var sEntityPath = "/" + oParameters.arguments.entity;
						// this.bindView(sEntityPath);

						// var oIconTabBar = oView.byId("idIconTabBar");
						// oIconTabBar.getItems().forEach(function(oItem) {
						// if (oItem.getKey() !== "selfInfo") {
						// oItem.bindElement(oItem.getKey());
						// }
						// });

						// // Specify the tab being focused
						// var sTabKey = oParameters.arguments.tab;
						// this.getEventBus().publish("Detail", "TabChanged", {
						// sTabKey: sTabKey
						// });

						// if (oIconTabBar.getSelectedKey() !== sTabKey) {
						// oIconTabBar.setSelectedKey(sTabKey);
						// }
						// }, this));

					},

					bindView : function(sEntityPath) {
						var oView = this.getView();
						oView.bindElement(sEntityPath);

						// Check if the data is already on the client
						if (!oView.getModel().getData(sEntityPath)) {

							// Check that the entity specified was found.
							oView
									.getElementBinding()
									.attachEventOnce(
											"dataReceived",
											jQuery
													.proxy(
															function() {
																var oData = oView
																		.getModel()
																		.getData(
																				sEntityPath);
																if (!oData) {
																	this
																			.showEmptyView();
																	this
																			.fireDetailNotFound();
																} else {
																	this
																			.fireDetailChanged(sEntityPath);
																}
															}, this));

						} else {
							this.fireDetailChanged(sEntityPath);
						}

					},

					showEmptyView : function() {
						this.getRouter().myNavToWithoutHash({
							currentView : this.getView(),
							targetViewName : "LMS_ODATA3.view.NotFound",
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

					onNavBack : function() {
						// This is only relevant when running on phone devices
						this.getRouter().myNavBack("main");
					},

					onDetailSelect : function(oEvent) {
						sap.ui.core.UIComponent.getRouterFor(this).navTo(
								"detail",
								{
									entity : oEvent.getSource()
											.getBindingContext().getPath()
											.slice(1),
									tab : oEvent.getParameter("selectedKey")
								}, true);
					},

					openActionSheet : function() {

						if (!this._oActionSheet) {
							this._oActionSheet = new sap.m.ActionSheet(
									{
										buttons : new sap.ushell.ui.footerbar.AddBookmarkButton()
									});
							this._oActionSheet.setShowCancelButton(true);
							this._oActionSheet
									.setPlacement(sap.m.PlacementType.Top);
						}

						this._oActionSheet.openBy(this.getView().byId(
								"actionButton"));
					},

					getEventBus : function() {
						return sap.ui.getCore().getEventBus();
					},

					getRouter : function() {
						return sap.ui.core.UIComponent.getRouterFor(this);
					},

					onExit : function(oEvent) {
						var oEventBus = this.getEventBus();
						oEventBus.unsubscribe("Master", "InitialLoadFinished",
								this.onMasterLoaded, this);
						oEventBus.unsubscribe("Component", "MetadataFailed",
								this.onMetadataFailed, this);
						if (this._oActionSheet) {
							this._oActionSheet.destroy();
							this._oActionSheet = null;
						}
					}
				});