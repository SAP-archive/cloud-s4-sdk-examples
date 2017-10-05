"use strict";
sap.ui.define([
        "sdk-tutorial-frontend/service/costcenters"
    ],
    function (CostCentersService) {
        //Create test data used for mocking and in the assertion
        var testCostCenters = [{
            "controllingArea": "A000",
            "name": "Cost Center 01"
        }]
        
        function getCostCentersPromise() {
            var jQueryPromise = new $.Deferred();
            return jQueryPromise.resolve(testCostCenters);
        }

        describe("Cost Center Service", function () {

            it("gets cost centers", function (done) {
                spyOn(jQuery, "ajax").and.returnValue(getCostCentersPromise());
                CostCentersService.getCostCenters("001").then(function (costCenters) {
                    expect(costCenters).toEqual(testCostCenters);
                    expect(jQuery.ajax).toHaveBeenCalled();
                    done();
                });

            });

        });
    });
