sap.ui.controller("Overview.home", {

	/**
	 * Called when a controller is instantiated and its View controls (if
	 * available) are already created. Can be used to modify the View before it
	 * is displayed, to bind event handlers and do other one-time
	 * initialization.
	 */
	onInit : function() {

		var oTileContainer = sap.ui.getCore().getElementById("tileContainer");

		/*// Admin Tool
		var adminStatus = jQuery.ajax({
			type : "GET",
			url : "/b/UITools.svc/AdminAlerts",
			statusCode : {
				500 : function() {
					oTileContainer.removeTile("adminTile");
				}
			}
		});

		// Audit Log
		var auditStatus = jQuery.ajax({
			type : "GET",
			url : "/b/UITools.svc/AuditLogs",
			statusCode : {
				500 : function() {
					oTileContainer.removeTile("auditTile");
				}
			}
		});

		// Scheduler
		var schedulerStatus = jQuery.ajax({
			type : "GET",
			url : "/b/UITools.svc/Schedules",
			statusCode : {
				500 : function() {
					oTileContainer.removeTile("schedulerTile");
				}
			}
		});
		
		// Scheduler
		var cudtomStatus = jQuery.ajax({
			type : "GET",
			url : "/b/UITools.svc/CustomFieldConfigs",
			statusCode : {
				500 : function() {
					oTileContainer.removeTile("customTile");
				}
			}
		});*/
		
		
		// Parameterized
		var cudtomStatus = jQuery.ajax({
			type : "GET",
			url : "/b/UITools.svc/CustomFieldConfigs",
			statusCode : {
				500 : function() {
					oTileContainer.removeTile("customTile");
				}
			}
		});
		
		// Reporting
		var reportStatus = jQuery.ajax({
			type : "GET",
			url : "/saplmsrepo/parameterizedfield",
			statusCode : {
				500 : function() {
					oTileContainer.removeTile("reportTile");
				}
			}
		});		
		
	},

/**
 * Similar to onAfterRendering, but this hook is invoked before the controller's
 * View is re-rendered (NOT before the first rendering! onInit() is used for
 * that one!).
 */
// onBeforeRendering: function() {
//
// },
/**
 * Called when the View has been rendered (so its HTML is part of the document).
 * Post-rendering manipulations of the HTML could be done here. This hook is the
 * same one that SAPUI5 controls get after being rendered.
 */
// onAfterRendering: function() {
//
// },
/**
 * Called when the Controller is destroyed. Use this one to free resources and
 * finalize activities.
 */
// onExit: function() {
//
// }
});
