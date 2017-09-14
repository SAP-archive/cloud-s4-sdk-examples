sap.ui.define([], function () {
    "use strict";

    return {
        getCostCenters: function (sapClient) {
            // Call API to retrieve list of cost centers for sap client 715
            return jQuery.get("/api/v1/rest/client/" + sapClient + "/costcenters")
                .then(function (data) {
                    return data;
                });
        },
        createCostCenter: function (id, text, sapClient) {
            // Create cost center object containing all predefined attributes
            var costCenter = {
                "controllingArea": "A000",
                "validFrom": "/Date(1451606400000)/",
                "validTo": "/Date(1483142400000)/",
                "name": "Cost Center 01",
                "category": "E",
                "personResponsible": "USER",
                "costCenterGroup": "0001",
                "companyCode": "1010",
                "profitCenter": "YB101"
            };

            costCenter.id = id;
            costCenter.description = text;
            costCenter.sapClient = sapClient;

            return jQuery.ajax({
                type: "POST",
                url: "/api/v1/rest/client/" + sapClient + "/costcenters",
                data: JSON.stringify(costCenter),
                contentType: "application/json"
            })
        }
    }
});
