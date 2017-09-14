"use strict";
/* eslint-disable max-nested-callbacks*/
sap.ui.define([
        "costcenter_app/service/costcenters"
    ],
    function (CostCentersService) {

        function getCostCentersPromise() {
            var jQueryPromise = new $.Deferred();
            return jQueryPromise.resolve([]);
        }

        describe("Cost Center Service", function () {

            it("gets cost centers", function (done) {
                spyOn(jQuery, "ajax").and.returnValue(getCostCentersPromise());
                CostCentersService.getCostCenters("001").then(function (costCenters) {
                    expect(costCenters).toEqual([]);
                    expect(jQuery.ajax).toHaveBeenCalled();
                    done();
                });

            });

        });
    });