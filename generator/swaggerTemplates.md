# Swagger templates for the VDM generator

The VDM generator reads a small subset of typical Swagger files that you get/generate from services such as the API Business Hub.

## Mandatory properties

For the purposes of generating code for custom services, you have to just fill the following:

- **basePath**: URL path to the OData service, *without* scheme, hostname, nor port. 
  
  For example, if you access the OData service directly at 
    
  ```https://my300532-api.s4hana.ondemand.com/sap/opu/odata/sap/YY1_S4HANASDK_CUSTOM_VDM_CDS```
    
    then you set
     
  ```"basePath" : "/sap/opu/odata/sap/YY1_S4HANASDK_CUSTOM_VDM_CDS"``` 
  
- **paths**: JSON object with properties for each OData entity set, plus allowed operations. For each entity set you can have a property that represents the "get all" endpoint and another property for the "get by key" endpoint.  For the "get by key" endpoint, just put empty round brackets at the end, no need to fill in the key information. Within each endpoint, set the value to a JSON object with ```"operation": {}``` properties for each allowed operation. 
  Example:
  ```
   "paths": {
     "/YY1_S4HANASDK_CUSTOM_VDM": {
       "get": {},
       "post": {}
     },
     "/YY1_S4HANASDK_CUSTOM_VDM()": {
       "get": {},
       "patch": {},
       "delete": {}
     }
   }
  ```

- **info**: This may become optional in the future, but for now the generator expects it to be set. It has three sub-properties:
  - **title**: Name to use for the Java classes and packages.
  - **description**: Text that goes in the javadocs of the service classes.
  - **version**: API version number that goes into the javadocs of the service classes, under a special heading. It was just to accommodate API Business Hub style of documentation. 