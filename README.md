# SAP S/4HANA Cloud SDK - Examples and Documentation
Runnable example applications that showcase the usage of the SAP S/4HANA Cloud SDK.

[![REUSE status](https://api.reuse.software/badge/github.com/SAP/cloud-s4-sdk-examples)](https://api.reuse.software/info/github.com/SAP/cloud-s4-sdk-examples)

 ## Description

 The [SAP Cloud SDK](https://sap.github.io/cloud-sdk/) helps to develop SAP S/4HANA and other  extension application on the SAP Cloud Platform. 

 This repository contains example projects that demonstrate, how developers can use SAP S/4HANA Cloud SDK components to build extensions for SAP S/4HANA on SAP Cloud Platform.  
 ### The following applications are included:
- [Cost Center Manager App with Spring Boot](https://github.com/SAP/cloud-s4-sdk-examples/tree/master/Costcenter-Controller-CF)
- [Cost Center Manager App with Java EE](https://github.com/SAP/cloud-s4-sdk-examples/tree/master/SDK-Tutorial-CF) 
- [Business Partner App](https://github.com/SAP/cloud-s4-sdk-examples/tree/learning) as explained in the [tutorials](https://blogs.sap.com/2017/05/10/first-steps-with-sap-s4hana-cloud-sdk/)
- [Employee Browser (Integration with S/4HANA and SuccessFactors)](https://github.com/SAP/cloud-s4-sdk-examples/tree/master/Employee-Browser-Neo)
- [Cost Center Creation Tool](https://github.com/SAP/cloud-s4-sdk-examples/tree/master/Cost-Center-Creation-Neo)
- [Multi-tenant persistence with PostgreSQL and Spring Boot](https://github.com/SAP/cloud-s4-sdk-examples/tree/master/Demo-Persistence)
- [S/4HANA - SAP Cloud Platform, Cloud Foundry connectivity ](https://github.com/SAP/cloud-s4-sdk-examples/tree/master/S4-Connectivity)
- [App2App Calls in SAP Cloud Platform, Cloud Foundry with S/4HANA Cloud SDK](https://github.com/SAP/cloud-s4-sdk-examples/tree/master/App2App)
- [Project demonstrating how to integrate the S/4HANA Cloud SDK VDM Generator](https://github.com/SAP/cloud-s4-sdk-examples/tree/master/VDMGenerator)

 ### SAP S/4HANA Cloud SDK Javadoc
- [Latest version](https://sap.github.io/cloud-s4-sdk-examples/docs/latest/javadoc-api/)

 ## Requirements
 
 In order to be able to deploy and test the examples, the following requirements need to be fulfilled from the infrastructure perspective: 
 
 Get your free trial [SAP Cloud Platform account](https://account.hanatrial.ondemand.com/register) to be able to run the examples on SAP Cloud Platform.  
 To evaluate the SAP S/4HANA connectivity, ensure that you have access to an SAP S/4HANA system (On-Premise or Cloud edition).
 
 ## Download and Installation
 
 We recommend to set up the following development tools for your convenience.
 
 ### Windows (Windows 7+ / Windows Server 2003+)
 - Install Chocolatey (a package manager for Windows)  
```@powershell -NoProfile -ExecutionPolicy Bypass -Command "iex ((New-Object System.Net.WebClient).DownloadString('https://chocolatey.org/install.ps1'))" && SET "PATH=%PATH%;%ALLUSERSPROFILE%\chocolatey\bin"```

For more information on Chocolatey and how to use it, visit the [following page](https://chocolatey.org/).

- Install Java Development Kit, if not yet availableInstall a specific JDK (e.g., JDK 8)  
``` choco install jdk8```

- Install Maven  
``` choco install maven```

 ### Mac
- Install Homebrew (Mac Packetmanager to help with the remaining installation)  
```/usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"```

- Install Java Development Kit, if not yet available  
``` 
brew update
brew cask install java
```

Hint: Install a specific JDK if you like with (e.g., JDK 8)  

``` brew cask install caskroom/versions/java8```
- Install Maven  
```
brew update
brew install maven
```

### Install IDE
For the initial analysis of the example application, you can just use your command line and a simple text editor. However, for your convenience, you can work with the IDE of your choice.

We recommend to use [Intellij IDEA](https://www.jetbrains.com/idea/#chooseYourEdition) or [Eclipse](https://www.eclipse.org/users/). Follow the installation instructions of the respective tool to prepare your IDE. In case you use Eclipse, make sure to install the Maven plugin for Eclipse.

When the IDE is installed, refer to additonal README files in the projects to proceed with the build and deployment procedures.

### Want to learn more? Check out our [Documentation](https://sap.github.io/cloud-sdk/)
For general overview check the [About section](https://sap.github.io/cloud-sdk/docs/overview/about)
[Getting started](https://sap.github.io/cloud-sdk/docs/java/getting-started) guide for SAP Cloud SDK for Java
[Getting started](https://sap.github.io/cloud-sdk/docs/js/getting-started) guide for SAP Cloud SDK for JavaScript
Look into other helpful feature documentation, guides and video-tutorials in respective section of documentation portal or use a smart search feature in the right top corner.

## Known Issues
 Currently, there are no known issues.

## How to obtain support
 If you need any support, have any question, or have found a bug, please report it as an issue in the repository.

