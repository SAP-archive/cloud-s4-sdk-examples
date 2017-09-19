---
layout: changelog
title: SAP S/4HANA Cloud SDK
---

# Changelog

## Version 1.1.1
_Coming soon_

New features:
- Added support for SAP Java Connector (JCo) for S/4HANA on-premise connectivity.
- Added support for "mvn tomee:run" and "mvn jetty:run" in TomEE and Tomcat archetypes for Cloud Foundry.

Breaking changes:
- The S/4HANA virtual data model for OData now uses the prefix "fetch" instead of "get" for navigation properties.
- Lombok now uses scope "provided" instead of "compile". 
  If your project uses Lombok, make sure to explicitly declare the usage of Lombok with scope "provided".

Changes:
- The DestinationAccessor class now throws DestinationOAuthTokenException instead of pure DestinationAccessException for missing OAuth tokens.
- The MockUtil class now throws TestConfigurationError instead of ShouldNotHappenException for test configuration issues.
- The MockUtil class now redirects audit logs to the default test log instead of ignoring them.
- Improve Javadoc of S/4HANA virtual data model.

Resolved issues:
- Fixed issue with missing audit logger in local containers.

## Version 1.0.0
_2017-09-14:_ [Maven Central](https://search.maven.org/#search%7Cga%7C1%7Cv%3A%221.0.0%22%20AND%20(g%3A%22com.sap.cloud.s4hana%22%20OR%20g%3A%22com.sap.cloud.s4hana.archetypes%22%20OR%20g%3A%22com.sap.cloud.s4hana.cloudplatform%22%20OR%20g%3A%22com.sap.cloud.s4hana.frameworks%22%20OR%20g%3A%22com.sap.cloud.s4hana.plugins%22%20OR%20g%3A%22com.sap.cloud.s4hana.quality%22%20OR%20g%3A%22com.sap.cloud.s4hana.starters%22))

- General availability of the SAP S/4HANA Cloud SDK.
