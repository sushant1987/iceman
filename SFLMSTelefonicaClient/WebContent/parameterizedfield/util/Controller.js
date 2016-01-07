jQuery.sap.declare("SFLMSExtCustomFieldConfig.util.Controller");

sap.ui.core.mvc.Controller.extend("SFLMSExtCustomFieldConfig.util.Controller", {
	getEventBus : function() {
		return sap.ui.getCore().getEventBus();
	},    


	getRouter : function() { 
		return sap.ui.core.UIComponent.getRouterFor(this);
	}
});