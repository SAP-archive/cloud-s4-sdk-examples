sap.ui.define([], function () {
    "use strict";
 
    return {
        getCostCenters: function () {
            return jQuery.get({
                url: "/costcenters",
                headers: {"X-CSRF-Token":"Fetch"}
            })
        }
    }  
});