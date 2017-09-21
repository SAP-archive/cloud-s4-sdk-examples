# Employeebrowser
Code Example for SFSF Extension openSAP Course

## Running Integration Tests 
Add file credentials.yml in the folder /<project root>/integration-tests/src/test/resources  

```
---
credentials:  
- alias: "<S/4HANA Alias>"  
  username: "<S/4HANA User>"  
  password: "<S/4HANA Password>"  
- alias: "<Success Factors Alias>"  
  username: "<Success Factors User>"  
  password: "<Success Factors Password>"  
 ```

## Local Deployment 
* Add credential information in ErpQueryEndpoint:
```
Name=ErpQueryEndpoint  
URL=<S/4HANA Endpoint URL>  
TrustAll=FALSE  
Type=HTTP  
Password=<S/4HANA Password>
Authentication=BasicAuthentication
User=<S/4HANA User>
```

* Add SuccessFactorsODataEndpoint  
```
Name=SuccessFactorsODataEndpoint
URL=<SFSF URL>
Type=HTTP
TrustAll=TRUE
Authentication=BasicAuthentication
User=<SFSF User>
Password=<SFSF Password>
```

* Execute the following commands in the project root  
```
mvn clean install   
mvn scp:clean scp:push -pl application  
```

## Neo Deployment
Run the following commands in the project root  
```
mvn clean install   
/path/to/neo deploy --host HOST --account ACCOUNT --user USER --application firstapp --source application/target/firstapp-app-1.0-SNAPSHOT.war   
/path/to/neo start --host HOST --account ACCOUNT --user USER --application firstapp  
```
After the deployment, maintain ErpQueryEndpoint and SuccessFactorsODataEndpoint in destinations for your application to be able to connect SAP S/4HANA and SAP Success Factors. 

Refer to the [blog post](https://blogs.sap.com/2017/05/21/step-2-with-sap-s4hana-cloud-sdk-helloworld-on-scp-classic/) for more instructions regarding the Neo landscape.


## RAML endpoints

 * `/api/v1/rest/companycodes`
 * `/api/v1/rest/costcenter-employees`
