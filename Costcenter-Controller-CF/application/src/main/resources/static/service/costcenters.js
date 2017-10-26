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
            // Target controlling area for new cost center
            var controllingArea = "A000";

            // Create cost center object containing all predefined attributes
            var costCenter = {
                "costcenter" : id,
                "descript" : text,
                "validFrom": 1451606400000,
                "validTo": 1483142400000,
                "name": id + " - " + text,
                "currency" : "EUR",
                "costcenterType": "E",          // Cost center category
                "personInCharge": "USER",       // Person responsible
                "costctrHierGrp": "0001",       // Cost center hierarchy group
                "compCode": "1010",             // Company code
                "profitCtr": "YB101"            // Profit center
            };

            return jQuery.ajax({
                type: "POST",
                url: "/api/v1/rest/client/" + sapClient + "/controllingarea/"+controllingArea+"/costcenters",
                data: JSON.stringify(costCenter),
                contentType: "application/json"
            })
        }
    }
});
