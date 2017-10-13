# Develop an S/4HANA Extension App on SAP Cloud Platform Cloud Foundry using the S/4HANA Cloud SDK
This repository contains learning materials for developing your first SAP S/4HANA Extension App using the [SAP S/4HANA Cloud SDK](https://www.sap.com/s4sdk)
* Corresponding presentation: [PDF](https://github.com/SAP/cloud-s4-sdk-examples/raw/learning/20171013_Partner%20Lecture%20Session_SDK.pdf)

## Step A
```
mvn archetype:generate '-DarchetypeGroupId=com.sap.cloud.s4hana.archetypes' '-DarchetypeArtifactId=scp-cf-tomee' '-DarchetypeVersion=1.1.2'
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
* Template for environment variable destinations
```
[{name: 'ErpQueryEndpoint', url: 'https://URL-to-S4HANA', username: '...', password: '...'}]
```
