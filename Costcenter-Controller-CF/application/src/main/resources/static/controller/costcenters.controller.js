sap.ui.define([
    "sap/ui/core/mvc/Controller",
    "sap/ui/model/json/JSONModel",
    "sap/m/MessageToast",
    "sap/ui/commons/MessageBox",
    "costcenter_app/service/costcenters"
], function (Controller, JSONModel, MessageToast, MessageBox, CostCenterService) {
    "use strict";

    return Controller.extend("costcenter_app.controller.costcenters", {


        createCostCenter: function () {
            var that = this;

            var id = this.getView().byId("ccID").getValue();
            var description = this.getView().byId("ccLT").getValue();

            CostCenterService.createCostCenter(id, description, "715").then(function (costCenters) {
                that.loadCostCenters();
            })
                .fail(function () {
                    MessageToast.show("Creating Costcenter failed!");
                });
        },

        loadCostCenters: function () {
            var that = this;
            CostCenterService.getCostCenters("715").then(function (costCenters) {
                costCenters = costCenters
                    .filter(function (a) {
                        return a.status != "DELETED";
                    })
                ;
                var model = new JSONModel(costCenters);
                that.getView().setModel(model, "costCenter");
            }).fail(function () {
                MessageToast.show("Loading Costcenters failed!");
            });
        },

        onInit: function () {
            this.loadCostCenters();
        },
    });
});
