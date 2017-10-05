sap.ui.define([
    "sap/ui/core/mvc/Controller",
    "sap/ui/model/json/JSONModel",
    "sap/m/MessageToast",
    "sdk-tutorial-frontend/service/costcenters"
], function(Controller, JSONModel, MessageToast, CostCenterService) {
    "use strict";
    return Controller.extend("sdk-tutorial-frontend.controller.View1", {

        createCostCenter: function () {
            var that = this;
            var costCenterData = {
                id : this.getView().byId("inputCostCenterId").getValue(),
                description : this.getView().byId("inputCostCenterDescription").getValue()
            };

            return jQuery.ajax({
                    type: "POST",
                    url: "/costcenters",
                    data: costCenterData
                })
                .then(function (costCenters) {
                    that.onInit();
                    MessageToast.show("Successfully created new cost center!");
                })
                .fail(function () {
                    MessageToast.show("Failed to create cost center!");
                });
        },

        onInit: function () {
            var view = this.getView();
             
            CostCenterService.getCostCenters()
                .done(function (data, status, jqXHR) {
                    var csrfToken = jqXHR.getResponseHeader("X-CSRF-Token");
                    $.ajaxSetup({
                        headers: {"X-CSRF-Token": csrfToken}
                    });
                    var model = new JSONModel(data);
                    view.setModel(model, "costCenter");
                });
        }
    });
});
