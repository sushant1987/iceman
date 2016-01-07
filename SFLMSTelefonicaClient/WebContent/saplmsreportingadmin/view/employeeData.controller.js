jQuery.sap.require("sap.m.MessageBox"); 
sap.ui.controller("LMS_ADMIN.view.employeeData", {
	
	/**
	 * Called when a controller is instantiated and its View controls (if available) are already created.
	 * Can be used to modify the View before it is displayed, to bind event handlers and do other one-time initialization.
	 * @memberOf LMS_ADMIN.view.offeringParticipant
	 */
		onInit: function() {
			this._loadConfigFields();
			
		},
		
		_loadData: function(oEvent){
			
			var employeeTab = this.getView().byId("idParticipantsTable");
			var actual = oEvent.getParameter("actual");
			var total = oEvent.getParameter("total");
			
			var comCode = this.getView().byId("CompCod").getValue();
			var employeeDataUrl = this.getOwnerComponent().getMetadata().getConfig().serviceConfig.serviceUrl + comCode+"?skip="+actual+"&top="+employeeTab.getGrowingThreshold();	
			var empDataModel = new sap.ui.model.json.JSONModel(employeeDataUrl);
			var that = this;
			empDataModel.attachRequestCompleted(function(){
				var deltaData = empDataModel.getData().data;
				var existData = that.getView().getModel().getData();
				Array.prototype.push.apply(existData.data, deltaData);
				that.getView().getModel().setData(existData);
			});
		},
		
		updateStarted: function(oEvent){
			if(oEvent.getParameter("reason") == "Growing"){
				this._loadData(oEvent);
			}
		},
		
		
		_loadConfigFields: function(){
			var configFieldUrl = this.getOwnerComponent().getMetadata().getConfig().serviceConfig.configFieldUrl;
			var fieldDataModel = new sap.ui.model.json.JSONModel(configFieldUrl);
			
			this.getView().byId("idCustomDataTable").setModel(fieldDataModel,"config");
			
			var that = this;
			fieldDataModel.attachRequestCompleted(function(){
				that._generateCustomDataTable(fieldDataModel.getData());
			})
		},
		
		_generateCustomDataTable: function(data){
			var ocusTable = this.getView().byId("idCustomDataTable");
			var columnTemplate = new sap.m.ColumnListItem({
				cells:[
					new sap.m.Text({
						text:"{Id}"
					})
				]
			});
			
			if(data && data.length){
				for(var i in data){
					if(data[i].name === "Id"){
						continue;
					}
					columnTemplate.addCell(
							new sap.m.Text({
						text:"{"+data[i].name+"}"
						})
					);
				}
			}
			
			ocusTable.bindItems({
			        path:"/data",
				sorter:new sap.ui.model.Sorter("id",false,false),
				template:columnTemplate
			});
		},
		
		dateFormat: function(val){
			if(val){
				var oDateFormat = sap.ui.core.format.DateFormat.getDateTimeInstance({pattern: "yyyy/MM/dd"});
				return oDateFormat.format(new Date(val));
			}
		},
		
		saveCustomData : function(index){
			
			var ocusTable = this.getView().byId("idCustomDataTable");
			var customTableData = ocusTable.getModel().getData();
			var employeeData = this.getView().getModel().getData();
			var that = this;
			if(employeeData){
				for(var i in employeeData){
					var custData = employeeData[i].customData;
					for(var j in custData){
						custData[j].value = customTableData[i][custData[j].name];
					}
				}
			}
			var updateUrl = this.getOwnerComponent().getMetadata().getConfig().serviceConfig.updateUrl;
			jQuery.ajax({
				url:updateUrl,
				dataType:"string",
				type:"PUT",
				contentType:"application/json; charset=utf-8",
				data:JSON.stringify(employeeData),
				success:function(data){
					that.onSearch();
				},error:function(){
					
				}
			});
			
		},
		onGoHome: function(oEvent) {
            var url = window.location.origin + "/"+location.pathname.split("/")[1];
            sap.m.URLHelper.redirect(url); 
       },

		
		handleIconTabBarSelect : function(oEvent){
			
		},
		
		onSearch: function(oEvent) {
			var employeeDataUrl = this.getOwnerComponent().getMetadata().getConfig().serviceConfig.serviceUrl;
			var comCode = this.getView().byId("CompCod").getValue();
			var employeeTab = this.getView().byId("idParticipantsTable");
			
			// we need to pass the skip and top for pagination
			var queryURL =  this.getOwnerComponent().getMetadata().getConfig().serviceConfig.serviceUrl+comCode+"?skip=0&top="+employeeTab.getGrowingThreshold();
			
			var employeeDataModel = new sap.ui.model.json.JSONModel(queryURL);
			this.getView().setModel(employeeDataModel);
			this.getView().setBusy(true);
			var that = this;
			employeeDataModel.attachRequestCompleted(function(){
				that.getView().setBusy(false);
			});
			
			var downloadLink = this.getOwnerComponent().getMetadata().getConfig().serviceConfig.downloadUrl + comCode;
			this.getView().byId("linkDownload").setHref(downloadLink);
		},

		uploadStart: function(oEvent){
			this.getView().setBusy(true);
		},
		uploadChange: function(oEvent){
			this.getView().setBusy(true);
		},
		uploadComplete:function(oEvent){
			jQuery.sap.require("sap.m.MessageToast");
			var sResponse = oEvent.getParameter("response");
			var rspJson = JSON.parse(sResponse.match(/{.*}/)[0]);
			if(rspJson.status){
				sap.m.MessageToast.show(rspJson.message);
			}else{
				sap.m.MessageToast.show("import finished with success count as: " + rspJson.successCount + " and failed count as: " + rspJson.failedCount);
			}
			
			this.onSearch();
		}
		
		

	/**
	 * Similar to onAfterRendering, but this hook is invoked before the controller's View is re-rendered
	 * (NOT before the first rendering! onInit() is used for that one!).
	 * @memberOf LMS_ADMIN.view.offeringParticipant
	 */
	//	onBeforeRendering: function() {
	//
	//	},

	/**
	 * Called when the View has been rendered (so its HTML is part of the document). Post-rendering manipulations of the HTML could be done here.
	 * This hook is the same one that SAPUI5 controls get after being rendered.
	 * @memberOf LMS_ADMIN.view.offeringParticipant
	 */
	//	onAfterRendering: function() {
	//
	//	},

	/**
	 * Called when the Controller is destroyed. Use this one to free resources and finalize activities.
	 * @memberOf LMS_ADMIN.view.offeringParticipant
	 */
	//	onExit: function() {
	//
	//	}

});
