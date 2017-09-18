# CostCenter Controller CF

## Build
```shell
mvn clean compile
```

## Test
```shell
mvn test
```

### Start Local Server
```shell
mvn spring-boot:run
```

## Run E2E Tests against Local Server
```shell
npm install
ENV_LAUNCH_URL=http://localhost:8080 npm run ci-e2e
```

## Shutdown local Server
```shell
curl -X POST localhost:8080/shutdown
```

## Deploy to CF
```shell
cf api https://api.cf.eu10.hana.ondemand.com
cf login
mvn clean package && cf push
```

## E2E Tests
Configuration reads hosts from environment, so set URL like this:
```shell
export ENV_LAUNCH_URL=https://costcenter-cf-spring-test.cfapps.eu10.hana.ondemand.com
```

- Run Tests
```shell
npm install
npm run ci-e2e
```

## Edit manifest.yml

- Host
- Endpoints

```
https://costcenter-cf-spring-<space>.cfapps.sap.hana.ondemand.com/index.html
```

## Useful CF Commands
```shell
cf logs costcenter-cf-spring --recent
```
