jQuery.sap.declare("SFLMSExtCustomFieldConfig.Component");
jQuery.sap.require("SFLMSExtCustomFieldConfig.MyRouter");

sap.ui.core.UIComponent.extend("SFLMSExtCustomFieldConfig.Component", {
	metadata : {
		name : "SFLMSExtCustomFieldConfig",
		version : "1.0",
		includes : [],
		dependencies : {
			libs : ["sap.m", "sap.ui.layout"],
			components : []
		},   

		rootView : "SFLMSExtCustomFieldConfig.view.App",

		config : {
			resourceBundle : "i18n/messageBundle.properties",
			serviceConfig : {
				name: "",
				serviceUrl: "/SFLMSTelefonicaClient/a/Parametrised",
				apikey : ""
			}
		},

		routing : {
			config : {
				routerClass : SFLMSExtCustomFieldConfig.MyRouter,
				viewType : "XML",
				viewPath : "SFLMSExtCustomFieldConfig.view",
				targetAggregation : "detailPages",
				clearTarget : false
			},
			routes : [
				{
					pattern : "",
					name : "main",
					view : "Master",
					targetAggregation : "masterPages",
					targetControl : "idAppControl",
					subroutes : [
						{
							pattern : "detail/{row}/{fieldId}/:tab:",
							name : "detail",
							view : "Detail"
						},
						{
							pattern : "new_detail",
							name : "newDetail",
							view : "Detail"
						}
					]
				},
				{
					name : "catchallMaster",
					view : "Master",
					targetAggregation : "masterPages",
					targetControl : "idAppControl",
					subroutes : [
						{
							pattern : ":all*:",
							name : "catchallDetail",
							view : "NotFound",
							transition : "show"
						}
					]
				}
			]
		}
	},

	init : function() {
		sap.ui.core.UIComponent.prototype.init.apply(this, arguments);

		var mConfig = this.getMetadata().getConfig();

		// always use absolute paths relative to our own component
		// (relative paths will fail if running in the Fiori Launchpad)
		var oRootPath = jQuery.sap.getModulePath("SFLMSExtCustomFieldConfig");

		// set i18n model
		var i18nModel = new sap.ui.model.resource.ResourceModel({
			bundleUrl : [oRootPath, mConfig.resourceBundle].join("/")
		});
		this.setModel(i18nModel, "i18n");
        
		var mHeaders = {"apikey" : mConfig.serviceConfig.apikey};
		
		this.loadParamterList();

		// set device model
		var oDeviceModel = new sap.ui.model.json.JSONModel({
			isTouch : sap.ui.Device.support.touch,
			isNoTouch : !sap.ui.Device.support.touch,
			isPhone : sap.ui.Device.system.phone,
			isNoPhone : !sap.ui.Device.system.phone,
			listMode : sap.ui.Device.system.phone ? "None" : "SingleSelectMaster",
			listItemType : sap.ui.Device.system.phone ? "Active" : "Inactive"
		});
		oDeviceModel.setDefaultBindingMode("OneWay");
		this.setModel(oDeviceModel, "device");

		this.getRouter().initialize();

	},

	loadParamterList : function(callback){
		var mConfig = this.getMetadata().getConfig();
		var sServiceUrl = mConfig.serviceConfig.serviceUrl;
		var oModel = new sap.ui.model.json.JSONModel(sServiceUrl);
		this.setModel(oModel);

		if(callback){
			oModel.attachRequestCompleted(function(){
				callback();
			})
		};
	},
	_startMockServer : function (sServiceUrl) {
		jQuery.sap.require("sap.ui.core.util.MockServer");
		var oMockServer = new sap.ui.core.util.MockServer({
			rootUri: sServiceUrl
		});

		var iDelay = +(jQuery.sap.getUriParameters().get("responderDelay") || 0);
		sap.ui.core.util.MockServer.config({
			autoRespondAfter : iDelay
		});

		oMockServer.simulate("model/metadata.xml", "model/");
		oMockServer.start();


		sap.m.MessageToast.show("Running in demo mode with mock data.", {
			duration: 2000
		});
	}
});