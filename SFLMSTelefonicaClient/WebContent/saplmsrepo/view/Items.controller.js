jQuery.sap.require("sap.ui.core.format.DateFormat");
sap.ui.core.mvc.Controller.extend("LMS_ODATA3.view.Items", {

	_queryInfo : {},

    onInit: function(){
		this.getRouter().getRoute("items").attachPatternMatched(this.onRouteMatched, this);
    },
    
    onNavBack: function() {
                    // This is only relevant when running on phone devices
                    // this.getRouter().myNavBack("detailPage");
                    window.history.go(-1); 

    },
    onRouteMatched: function(oEvent){
        var oJsonModel = new sap.ui.model.json.JSONModel();
        var legalEntity = oEvent.getParameter("arguments").legalEntity;
        var query = oEvent.getParameter("arguments")['?query'];
        
      //save the queryinfo for later download
		this._queryInfo = {
			legalEntity: legalEntity,
			itemId: query.itemId,
			dateSel: query.dateSel,
			runid: query.runId
		};
        
        var itemServiceURL = this.getOwnerComponent().getMetadata().getConfig().serviceConfig.itemServiceUrl;
        
        itemServiceURL = this.getOwnerComponent().setURLParameters(itemServiceURL, [this._queryInfo.legalEntity, 
                        this._queryInfo.itemId, this._queryInfo.dateSel,this._queryInfo.runid ]);
        // var itemServiceURL = "model/ItemsSet.json";
        oJsonModel.loadData(itemServiceURL);
        
        var oTables = this.getView().byId("itemTable");
		oTables.setBusy(true);
//		var ojsonModel = new sap.ui.model.json.JSONModel(
//				queryURL);
		oJsonModel.attachRequestCompleted(function() {
			oTables.setBusy(false);
		});
		
        this.getView().setModel(oJsonModel);
        

		if(!legalEntity){
			return;
		}
//		var oTables = this.getView().byId("itemTable");
//		oTables.setBusy(true);
//		var ojsonModel = new sap.ui.model.json.JSONModel(
//				queryURL);
//		ojsonModel.attachRequestCompleted(function() {
//			ovTable.setBusy(false);
//		});
/*			var oFilter = new sap.ui.model.Filter("LegalEntity", sap.ui.model.FilterOperator.EQ, legalEntity);
	    	var oTables = [this.getView().byId("itemTable"),
						   this.getView().byId("durationTable"),
						   this.getView().byId("observationTable"),
						   this.getView().byId("parameterizedTable")];
			for(var i = 0 ; i < oTables.length; i++){
				oTables[i].bindRows({
					path:"/",
					filters:[oFilter]
				});
			} */

    },
    getRouter: function() {
		return sap.ui.core.UIComponent.getRouterFor(this);
	},
	formatDate: function(val){
		if(val){
			var tagetFormat = sap.ui.core.format.DateFormat.getDateInstance("MMM d,y");
			var sddate = new Date(parseFloat(val.match("\\d+")[0]));
			if(sddate){
				var sDateString = tagetFormat.format(sddate);
				return sDateString;
			}else{
				return val;
			}
		}
		return val;
	},
	
	sortItemCode : function(oEvent){
		var oView = this.getView();
		var oTable = oView.byId("itemTable");
		oTable.sort(oView.byId("itemcode"), SortOrder.Ascending, false);
//		oTable.sort(oView.byId("name"), SortOrder.Ascending, true);
	},
	
	onPress: function(){
		var fileURL = this.getOwnerComponent().setURLParameters("/SFLMSTelefonicaClient/a/ItemReport/{legalEntity}/{id}/{date}/{runid}",[this._queryInfo.legalEntity, 
		this._queryInfo.itemId, this._queryInfo.dateSel,this._queryInfo.runid ]);
		var link = document.createElement("a");
	    link.download = "Items.xml";
	    link.href = fileURL;
	    link.click();
	    var items = "Items";
		this.getRouter().navTo("detail", {reportType : items , genreport : true});
	}

});