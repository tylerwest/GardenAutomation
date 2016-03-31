# Greenhose

## Introduction
This project is being developed to fully automate my backyard greenhouse. Basic functions include monitoring soil moisture content, monitoring the ambient temperature and humidity inside the greenhouse, regulating the flow of water into the soil, and opening and closing the windows.

## Hardware
- 6 x 12V solenoid valves
- 6 x Capacitative soil moisture sensors (board design here: http://zerocharactersleft.blogspot.ca/2011/11/pcb-as-capacitive-soil-moisture-sensor.html)

## Requirements
- Java 8
- Tomcat 7.x

## Libraries
- Vaadin 7.6.4
- Pi4J 1.0

## Workflow
To compile the entire project, run "mvn clean" and "mvn install".

To run the application, deploy the generated "greenhouse.war" to Tomcat and open http://localhost:8080/greenhouse.
Alternatively, run the project in Eclipse via a local Tomcat instance.

To produce a deployable production mode WAR:
- Change productionMode to true in the servlet class configuration (nested in the GreenhouseUI class)
- Run "mvn clean package"
- Deploy the generated "greenhouse.war" to Tomcat


