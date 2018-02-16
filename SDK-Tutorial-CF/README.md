# SAP S/4HANA Cloud SDK - Tutorial on Cloud Foundry
The code in this repository implements the [SAP S/4HANA Cloud SDK Tutorial](https://blogs.sap.com/2017/05/10/first-steps-with-sap-s4hana-cloud-sdk/) on SAP Cloud Platform Cloud Foundry (all steps up to and including step 13, except step 2 & 8, which are specific to Neo).
This is meant as a place of reference to look at if you are unsure how to implement something.
Keep in mind that this reflects the state after step 13, so some things may have changed compared to earlier steps (introduction of Hystrix commands, Virtual Data Model, frontend, ...).

Please give your feedback directly in the comments of the tutorial blog posts on blogs.sap.com.

## How to Run ...
The following are only minimal instructions. Please adhere to the instructions outlined in the tutorial steps to get the example running with all components.
### ... the Backend
* If you want to run tests: add your ERP system URL to _integration-tests/src/test/resources/systems.json_ and provide the credentials as explained in [step 5](https://blogs.sap.com/2017/06/23/step-5-resilience-with-hystrix/)
```
mvn clean package -Dtest.credentials=/path/to/credentials.yml
```
* Alternatively, if you don't have an ERP available and just want to check the basics, use
```
mvn clean package -Dmaven.test.skip=true
```
* Afterwards, deploy to Cloud Foundry as explained in [step 3](https://blogs.sap.com/2017/05/19/step-3-with-sap-s4hana-cloud-sdk-helloworld-on-scp-cloudfoundry/)
 * If you want a specific host name, put your unique hostname into _manifest.yml_ and remove the `random-route: true` property
* Backend security is disabled to make getting started easier. Follow [step 7](https://blogs.sap.com/2017/07/18/step-7-with-sap-s4hana-cloud-sdk-secure-your-application-on-sap-cloud-platform-cloudfoundry/) of our tutorial series and re-enable the security in the _web.xml_ file by un-commenting the respective lines.

### ... with Security
In general, please follow [step 7](https://blogs.sap.com/2017/07/18/step-7-with-sap-s4hana-cloud-sdk-secure-your-application-on-sap-cloud-platform-cloudfoundry/).
* The app router application resides in the subfolder _security_.
* Adapt the file _manifest.yml_: provide your UAA service instance
* Adapt the file _security/manifest.yml_: unique host, destination URLs, UAA service instance
* Adapt the file _security/xs-security.json_: xsappname

### ... the Frontend
In general, please follow [step 9](https://blogs.sap.com/2017/09/09/step-9-with-sap-s4hana-cloud-sdk-implement-and-deploy-a-frontend-application/).
* The frontend application resides in the subfolder _webapp_.
* Like the backend, the _manifest.yml_ uses a random route.