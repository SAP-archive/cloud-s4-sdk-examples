# Cost Center Creation Tool 
Showcase application to demonstrate  BAPI invocation using the SAP S/4HANA Cloud SDK.

This application retrieves Cost Centers from a given Controlling Area, uses one Cost Center as reference, and 
creates additional Cost Centers based on the reference. The name of the new Cost Centers consists of a specified 
prefix plus an increased suffix.

Example:
- Parameter namingPrefix specified as "CC"
- Parameter namingSuffixStartCounter specified as 1
- Parameter numberOfCreations specified as 3
- The application creates Cost Centers CC1, CC2, and CC3

## Running Integration Tests 
Add file credentials.yml in the folder <project root>/integration-tests/src/test/resources.  

```
---
credentials:  
- alias: "ErpQueryEndpoint"  
  username: "<S/4HANA User>"  
  password: "<S/4HANA Password>"   
 ```

Add file systems.yml in the folder <project root>/integration-tests/src/test/resources.  

```
---
erp:
  default: "ErpQueryEndpoint"
  systems:
    - alias: "ErpQueryEndpoint"
      sapClient: "<sapClient>"
      systemId: "<SystemIdentifier>"
      applicationServer: "<applicationServer>"
      instanceNumber: "<instanceNumber>"
 ```

Adjust the test case parameters in method testService in class CostCenterCreationServletTest.
Maintain the test case parameters according to the ERP system you connect to:
- controllingArea: Specify the Controlling Area in which you want to create Cost Centers.
- costCenterIndex: Decide which Cost Center in the given Controlling Area is considered as reference for the new Cost Centers.
- numberOfCreations: Decide how many Cost Centers shall be created.
- namingPrefix: Specify how the names of the new Cost Centers shall start.
- namingSuffixStartCounter: Specify the start counter for the naming suffix.

* Execute the following commands in the project root  
```
mvn clean install   
```

