sap.ui.define([
    "sap/ui/core/mvc/Controller",
    "sap/ui/model/json/JSONModel",
    "sap/m/MessageToast"
], function(Controller, JSONModel, MessageToast) {
    "use strict";
    return Controller.extend("employee-browser-frontend.controller.View1", {

        onInit: function() {
            var view = this.getView();
            jQuery.get("/api/v1/rest/companycodes")
                .done(function (data) {
                    // expose available company codes to view
                    view.setModel(new JSONModel(data), "Filter");
                    view.byId("selectorCompanyCode").setEnabled(true);
                });
        },

        selectCompanyCode: function() {
            var view = this.getView();
            var companyCode = view.byId("selectorCompanyCode").getValue();
            var parameter = companyCode ? { "companyCode" : companyCode } : null;
            view.byId("employeeList").setNoDataText(companyCode ? "Loading..." : "Loading all...");
            jQuery.get("/api/v1/rest/costcenter-employees", parameter)
                .done(function (data) {
                    // expose all employee data to view
                    view.setModel(new JSONModel(data), "Result");
                    // in case of a problem
                    if(!data || !data.length) {
                        view.byId("employeeList").setNoDataText(
                            "No employees found"
                            + (companyCode ? " for company code \""+companyCode+"\"" : "")
                            + ".");
                    }
                })
                .fail(function () {
                    view.setModel(new JSONModel([]), "Result");
                    view.byId("employeeList").setNoDataText("Failed to retrieve employees.");
                });
        }
    });
});
