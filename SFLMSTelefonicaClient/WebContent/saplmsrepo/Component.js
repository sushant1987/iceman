jQuery.sap.declare("LMS_ODATA3.Component");
// jQuery.sap.require("LMS_ODATA3.MyRouter");
jQuery.sap.require("sap.ui.core.UIComponent");
jQuery.sap.require("sap.ui.core.routing.Target");
jQuery.sap.require("sap.m.routing.RouteMatchedHandler");
jQuery.sap.require("sap.ui.core.routing.Router");

sap.ui.core.UIComponent.extend("LMS_ODATA3.Component", {
	metadata: {
		name: "LMS_ODATA3",
		version: "1.0",
		includes: [],
		dependencies: {
			libs: ["sap.m", "sap.ui.layout"],
			components: []
		},

		rootView: "LMS_ODATA3.view.App",

		config: {
			resourceBundle: "i18n/messageBundle.properties",
			serviceConfig: {
				overview:{
					item:"/SFLMSTelefonicaClient/a/OverviewScreen/Items/{legalEntity}/{id}/{date}",
					compOffer:"/SFLMSTelefonicaClient/a/OverviewScreen/CompletedOffering/{legalEntity}/{id}/{date}",
					newOffer:"/SFLMSTelefonicaClient/a/OverviewScreen/NewOffering/{legalEntity}/{id}/{days}/{date}",
					participant:"/SFLMSTelefonicaClient/a/OverviewScreen/Participants/{comCode}/{id}/{date}",
					reportHistory:"/SFLMSTelefonicaClient/a/ReportInfo/{legalEntity}/{id}/{date}",
					validation:"/SFLMSTelefonicaClient/a/Validation/{offering}/{type}" 
						
						// reportHistory:"model/ReportInfo/{legalEntity}/{id}/{date}"
				},
				itemServiceUrl:"/SFLMSTelefonicaClient/a/Items/{legalEntity}/{id}/{date}/{runid}",
				completeOfferingServiceUrl:"/SFLMSTelefonicaClient/a/CompletedOffering/{legalEntity}/{id}/{date}/{runid}",
				newOfferingServiceUrl:"/SFLMSTelefonicaClient/a/NewOffering/{legalEntity}/{id}/{days}/{date}/{runid}",
//				participantsServiceUrl:"/SFLMSTelefonicaClient/a/ParticipantsReport/{legalEntity}/{id}/{date}/{runid}",
				participantsServiceUrl:"/SFLMSTelefonicaClient/a/Participants/{comCode}/{id}/{date}/{runid}",
				dowloadServiceUrl:"dowloadPath"  
			}
		},

		routing: {
			config: {
				routerClass:sap.m.routing.Router,
				viewType: "XML",
				viewPath: "LMS_ODATA3.view",
				clearTarget: false,
				controlId: "idAppControl"				
			},
			routes: [{
				pattern: "",
				name: "main",
				target:"master",
				subroutes:[
					{
							pattern: "detail/{reportType}/:genreport:",
							name: "detail",
							target: ["detail","master"]
					},
					{
							name: "items",
							pattern: "d_items/{legalEntity}:?query:", 
							target: "items"
				    },
				    {
							name: "offeringnew",
							pattern: "d_offeringnew/{legalEntity}:?query:", 
							target: "offeringnew"
				    },
				    {
							name: "offeringcomp",
							pattern: "d_offeringcomp/{legalEntity}:?query:", 
							target: "offeringcomp"
				    },
				    {
							name: "participants",
							pattern: "d_participants/{comCode}:?query:",
							target: "participants"
				    },
				    {
							name: "errorlog",
							pattern: "d_errorlog",
							target: "errorlog"
				    },
				     {
							name: "runidinfo",
							// pattern: "d_runidinfo/{LegalEntity}:?query:",
							pattern: "d_runidinfo",
							target: "runidinfo"
				    },
				    {
						name: "detailparticipant",
						// pattern: "d_runidinfo/{LegalEntity}:?query:",
						pattern: "d_detailparticipant/:genreport:",
						target: "detailparticipant"
				    }
				    
				]
			},
			{
				name: "catchallMaster",
				view: "Master",
				targetAggregation: "masterPages",
				targetControl: "idAppControl",
				subroutes: [{
					pattern: ":all*:",
					name: "catchallDetail",
					view: "NotFound",
					transition: "show"
				}]
			}],
		targets: {	
					master:{
						viewName:"Master",
						controlAggregation:"masterPages"
					},
					detail: {
						viewName: "Detail",
						controlAggregation: "detailPages"
					},
					items: {
						viewName: "Items",
						controlAggregation: "detailPages"
					},
					offeringnew: {
						viewName: "ScheduledOffering",
						controlAggregation: "detailPages"
					},
					offeringcomp: {
						viewName: "CompletedOffering",
						controlAggregation: "detailPages"
					},
					participants: {
						viewName: "Participants",
						controlAggregation: "detailPages"
					},
					errorlog: {
						viewName: "Errorlog",
						controlAggregation: "detailPages"
					},
					runidinfo: {
						viewName: "RunIDInfo",
						controlAggregation: "detailPages"
					},
					detailparticipant: {
						viewName: "DetailParticipant",
						controlAggregation: "detailPages"
					},
					NotFound:{
						viewName: "NotFound",
						controlAggregation: "detailPages"
					}
				}			
		}
	},

	init: function() {
		sap.ui.core.UIComponent.prototype.init.apply(this, arguments);

		var mConfig = this.getMetadata().getConfig();

		// Always use absolute paths relative to our own component
		// (relative paths will fail if running in the Fiori Launchpad)
		var oRootPath = jQuery.sap.getModulePath("LMS_ODATA3");

		// Set i18n model
		var i18nModel = new sap.ui.model.resource.ResourceModel({
			bundleUrl: [oRootPath, mConfig.resourceBundle].join("/")
		});
		this.setModel(i18nModel, "i18n");

		// var sServiceUrl = mConfig.serviceConfig.serviceUrl;

		//This code is only needed for testing the application when there is no local proxy available
		
		//abondon the OData , let's embrace the json ap
		// var bIsMocked = jQuery.sap.getUriParameters().get("responderOn") === "true";
		
		// bIsMocked = true;
		// // Start the mock server for the domain model
		// if (bIsMocked) {
		// 	this._startMockServer(sServiceUrl);
		// }

		// Create and set domain model to the component
		// var oModel = new sap.ui.model.odata.v2.ODataModel(sServiceUrl, {
		// 	json: true,
		// 	loadMetadataAsync: true
		// });
		// oModel.attachMetadataFailed(function() {
		// 	this.getEventBus().publish("Component", "MetadataFailed");
		// }, this);
		// this.setModel(oModel);

		// Set device model
		var oDeviceModel = new sap.ui.model.json.JSONModel({
			isTouch: sap.ui.Device.support.touch,
			isNoTouch: !sap.ui.Device.support.touch,
			isPhone: sap.ui.Device.system.phone,
			isNoPhone: !sap.ui.Device.system.phone,
			listMode: sap.ui.Device.system.phone ? "None" : "SingleSelectMaster",
			listItemType: sap.ui.Device.system.phone ? "Active" : "Inactive"
		});
		oDeviceModel.setDefaultBindingMode("OneWay");
		this.setModel(oDeviceModel, "device");

		this.getRouter().initialize();
	},

	_startMockServer: function(sServiceUrl) {
		jQuery.sap.require("sap.ui.core.util.MockServer");
		var oMockServer = new sap.ui.core.util.MockServer({
			rootUri: sServiceUrl
		});

		var iDelay = +(jQuery.sap.getUriParameters().get("responderDelay") || 0);
		sap.ui.core.util.MockServer.config({
			autoRespondAfter: iDelay
		});

		oMockServer.simulate("model/metadata.xml", "model/");
		oMockServer.start();

		// sap.m.MessageToast.show("Running in demo mode with mock data.", {
		// 	duration: 4000
		// });
	},
	
	setURLParameters:function(serviceUrl, paramList){
		var url = serviceUrl;
		var params = url.match(/{[^\/]+}/g);
		for(var i = 0 ; i < params.length; i++){
			var value = paramList[i];
			if(!value){
				value = "none";
			}
			url = url.replace(params[i], value);
		}
		return url;
	},

	getEventBus: function() {
		return sap.ui.getCore().getEventBus();
	}
});