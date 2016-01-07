jQuery.sap.declare("LMS_ADMIN.Component");
jQuery.sap.require("sap.ui.core.UIComponent");
jQuery.sap.require("sap.ui.core.routing.Target");
jQuery.sap.require("sap.m.routing.RouteMatchedHandler");
jQuery.sap.require("sap.ui.core.routing.Router");

sap.ui.core.UIComponent.extend("LMS_ADMIN.Component", {
	metadata: {
		name: "LMS_ADMIN", 
		version: "1.0", 
		includes: [],
		dependencies: {
			libs: ["sap.m", "sap.ui.layout"],
			components: []
		},
   
		rootView: "LMS_ADMIN.view.App",

		config: {
			resourceBundle: "i18n/messageBundle.properties",
			serviceConfig: {
				serviceUrl:"/SFLMSTelefonicaClient/a/EmployeeData/", 
				updateUrl:"/SFLMSTelefonicaClient/a/AddCustom",
				configFieldUrl:"/SFLMSTelefonicaClient/a/CustomFieldConfig",
				downloadUrl:"/SFLMSTelefonicaClient/a/downloadEMPCSV/"
			} 
		},

		routing: {
			config: {
				routerClass:sap.m.routing.Router,
				viewType: "XML",
				viewPath: "LMS_ADMIN.view",
				clearTarget: false,
				controlId: "idAppControl"				
			},
			routes: [{
				pattern: "",
				name: "main",
				target:"main"
			},
			{
				name: "catchallMaster",
				view: "Master",
				targetAggregation: "pages",
				targetControl: "idAppControl",
				subroutes: [{
					pattern: ":all*:",
					name: "catchallDetail",
					view: "NotFound",
					transition: "show"
				}]
			}],
			targets: {	
				main:{
					viewName:"employeeData",
					controlAggregation:"pages"
				}		
			}
		}
	},

	init: function() {
		sap.ui.core.UIComponent.prototype.init.apply(this, arguments);

		var mConfig = this.getMetadata().getConfig();

		// Always use absolute paths relative to our own component
		// (relative paths will fail if running in the Fiori Launchpad)
		var oRootPath = jQuery.sap.getModulePath("LMS_ADMIN");

		// Set i18n model
		var i18nModel = new sap.ui.model.resource.ResourceModel({
			bundleUrl: [oRootPath, mConfig.resourceBundle].join("/")
		});
		this.setModel(i18nModel, "i18n");

		// var sServiceUrl = mConfig.serviceConfig.serviceUrl;

		// //This code is only needed for testing the application when there is no local proxy available
		// var bIsMocked = jQuery.sap.getUriParameters().get("responderOn") === "true";
		// // Start the mock server for the domain model
		// if (bIsMocked) {
		// 	this._startMockServer(sServiceUrl);
		// }

		// // Create and set domain model to the component
		// var oModel = new sap.ui.model.json.JSONModel(sServiceUrl);
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

		oMockServer.simulate("model/metadata.xml", {
                'sMockdataBaseUrl' : "model/",
                'bGenerateMissingMockData' : true
        });
		oMockServer.start();

		// sap.m.MessageToast.show("Running in demo mode with mock data.", {
		// 	duration: 4000
		// });
	},

	getEventBus: function() {
		return sap.ui.getCore().getEventBus();
	}
});