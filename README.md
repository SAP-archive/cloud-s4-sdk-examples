# SAP S/4HANA Cloud SDK - Examples and Documentation
Runnable example applications that showcase the usage of the SAP S/4HANA Cloud SDK.

 ## Description

 The [SAP S/4HANA Cloud SDK](https://sap.com/s4sdk) helps to develop SAP S/4HANA extension application on the SAP Cloud Platform. 

 This repository contains example projects that demonstrate, how developers can use SAP S/4HANA Cloud SDK components to build extensions for SAP S/4HANA on SAP Cloud Platform.  
 ### The following applications are included:
- Cost Center Manager App
- Cost Center Manager App as explained in the [tutorials](https://blogs.sap.com/2017/05/10/first-steps-with-sap-s4hana-cloud-sdk/)
- Employee Browser (Integration with S/4HANA and SuccessFactors)

 ### SAP S/4HANA Cloud SDK Javadoc
- [1.1.1 (latest)](https://sap.github.io/cloud-s4-sdk-examples/docs/1.1.1/javadoc-api/)
- [1.0.0](https://sap.github.io/cloud-s4-sdk-examples/docs/1.0.0/javadoc-api/)

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

## Known Issues
 Currently, there are no known issues.

## How to obtain support
 If you need any support, have any question, or have found a bug, please report it as an issue in the repository.

## License
 Copyright (c) 2017 SAP SE or an SAP affiliate company. All rights reserved.
 This file is licensed under the Apache Software License, v. 2 except as noted otherwise in the [LICENSE file](LICENSE).
