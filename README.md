# Develop an S/4HANA Extension App on SAP Cloud Platform Cloud Foundry using the SAP S/4HANA Cloud SDK
This repository contains learning materials for developing your first SAP S/4HANA Extension App using the [SAP S/4HANA Cloud SDK](https://www.sap.com/s4sdk)
* Corresponding presentation: [PDF](https://github.com/SAP/cloud-s4-sdk-examples/raw/learning-latest/SAP%20S4HANA%20Cloud%20SDK-Training.pdf)

## Step A
```
mvn archetype:generate '-DarchetypeGroupId=com.sap.cloud.s4hana.archetypes' '-DarchetypeArtifactId=scp-cf-tomee' '-DarchetypeVersion=1.3.0'
```
The example uses the following parameters for the archetype:
* groupId: `com.sap.cloud.s4hana.tutorial`
* artifactId: `s4sdk-learning`
* unqieHostname: choose your own host name that is unique across SAP Cloud Platform

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