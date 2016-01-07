sap.m.Table.extend("LMS_ADMIN.JSONList",{
	metadata:{
		properties:{
			totalCount: {type:"int", defaultValue:-1}
		}
	},
	renderer: "sap.m.TableRenderer"

});

LMS_ADMIN.JSONList.prototype.init = function() {
	sap.m.Table.prototype.init.call(this);
};
	
LMS_ADMIN.JSONList.prototype.getMaxItemsCount = function(){
	if(!this.getTotalCount || this.getTotalCount() == -1){
		return sap.m.Table.prototype.getMaxItemsCount.apply(this, arguments);
	}else{
		return this.getTotalCount();
	}
}

LMS_ADMIN.JSONList.prototype.onBeforePageLoaded = function(oGrowingInfo, sChangeReason) {

	//if only growing is fired, upload the list
	if(sChangeReason === "Growing"){
		this._fireUpdateStarted(sChangeReason, oGrowingInfo);
		this.fireGrowingStarted(oGrowingInfo);
	}
};

LMS_ADMIN.JSONList.prototype.exit = function () {
	sap.m.Table.prototype.prototype.exit.call(this);
};