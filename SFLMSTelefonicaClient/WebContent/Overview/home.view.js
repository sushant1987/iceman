sap.ui.jsview("Overview.home", {
 
          getControllerName : function() {
        	  	return "Overview.home";
          },
 
          createContent : function(oController) {
                    
                    var tiles = new sap.m.Page({
                        title : "LMS Reporting Spain",
                        content:[
                     new sap.m.TileContainer({
                    	id : "tileContainer",
                		tiles : [ new sap.m.StandardTile({
                			id: "generateReport",
                			icon : "sap-icon://pie-chart",
                			title : "Generate Report", 
                			press : function() {
                				var rootpath = window.location.href;
                				var newurl = rootpath + "saplmsrepo/index.html";
                				
                				window.location.replace(newurl);             				
                			}

                		}),
                		
                		new sap.m.StandardTile({
                 			icon : "sap-icon://add-activity-2",
                 			id: "parameterizedField",
                			title : "Maintain Parameterized Field",
                			press : function (){
                				var rootpath = window.location.href;
                				var newurl = rootpath + "parameterizedfield/index.html";
                				window.location.replace(newurl);
                			}
                		}),
                		
                		new sap.m.StandardTile({
                 			icon : "sap-icon://add-activity-2",
                 			id: "saplmsreportingadmin",
                			title : "Maintain Employee Information",
                			press : function (){
                				var rootpath = window.location.href;
                				var newurl = rootpath + "saplmsreportingadmin/index.html";
                				window.location.replace(newurl);
                			}
                		}),
                		
                						
                		],

                	})
                     ] });
                     
                    return tiles;     
          }
});