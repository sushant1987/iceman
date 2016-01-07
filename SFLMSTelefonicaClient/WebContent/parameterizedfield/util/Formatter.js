jQuery.sap.require("sap.ui.core.format.DateFormat");
jQuery.sap.declare("SFLMSExtCustomFieldConfig.util.Formatter");

SFLMSExtCustomFieldConfig.util.Formatter = {

	uppercaseFirstChar : function(sStr) {
		return sStr.charAt(0).toUpperCase() + sStr.slice(1);
	},   


	discontinuedStatusState : function(sStatus) {
		return sStatus ? "Error" : "None";
	},

	discontinuedStatusValue : function(sStatus) {
		return sStatus ? "Discontinued" : "";
	},
	
	convertDate : function(sDate) {
		if (sDate) {
			var sourceFormat = sap.ui.core.format.DateFormat.getDateInstance("dd/MM/yyyy");
			var tagetFormat = sap.ui.core.format.DateFormat.getDateInstance({pattern:"MMM d, y",style:"medium"});
			var sddate = new Date(sourceFormat.parse(sDate));
			if(sddate){
				var sDateString = tagetFormat.format(sddate);
				return sDateString;
			}else{
				return sDate;
			}
		}else {
			return sDate;
		}
	},
	
	parseDate: function(sDate){
		var sourceFormat = sap.ui.core.format.DateFormat.getDateInstance("dd/MM/yyyy");
		if(sDate){
			return new Date(sourceFormat.parse(sDate));
		}
		return new Date();
	},
	convertStatusState : function(sStatus) {
	    if (sStatus === "In Process" || sStatus === "New") {
	        return "None";
	    }
	    else if (sStatus === "Rejected") {
	        return "Error";
	    }
	    else if (sStatus === "Approved") {
	        return "Success";
	    }
	},
	
	currencyValue : function(value) {
		return parseFloat(value).toFixed(2);
	}

};