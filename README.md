# Develop an S/4HANA Extension App on SAP Cloud Platform Cloud Foundry using the S/4HANA Cloud SDK
This repository contains learning materials for developing your first S/4HANA Extension App using the [SAP S/4HANA Cloud SDK](https://www.sap.com/s4sdk)

## Step A
```
mvn archetype:generate '-DarchetypeGroupId=com.sap.cloud.s4hana.archetypes' '-DarchetypeArtifactId=scp-cf-tomee' '-DarchetypeVersion=1.1.1'
```

## Step B
* https://cloudplatform.sap.com/try.html
```
mvn clean package
cf api https://api.cf.eu10.hana.ondemand.com
cf login
cf push
```

## Step C
* [BusinessPartnerServlet](https://github.wdf.sap.corp/raw/D061607/s4sdk-learning/stepC/application/src/main/java/com/sap/cloud/s4hana/tutorial/BusinessPartnerServlet.java)
* Template for environment variable destinations
```
[{name: 'ErpQueryEndpoint', url: 'https://my300098.s4hana.ondemand.com', username: '...', password: '...'}]
```

### Test
* [BusinessPartnerServiceTest](https://github.wdf.sap.corp/raw/D061607/s4sdk-learning/stepC/integration-tests/src/test/java/com/sap/cloud/s4hana/tutorial/BusinessPartnerServiceTest.java)
* [systems.json](https://github.wdf.sap.corp/raw/D061607/s4sdk-learning/stepC/integration-tests/src/test/resources/systems.json)
* [Template for credentials.yml](https://github.wdf.sap.corp/raw/D061607/s4sdk-learning/stepC/integration-tests/src/test/resources/credentials.yml.template)

## Step D
* [Template for GetBusinessPartnersCommand](https://github.wdf.sap.corp/raw/D061607/s4sdk-learning/stepD-prep/application/src/main/java/com/sap/cloud/s4hana/tutorial/GetBusinessPartnersCommand.java)
* ALLOW_MOCKED_AUTH_HEADER
