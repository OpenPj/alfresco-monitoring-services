# Alfresco Monitoring Services
This component was initially created during the Alfresco's Global Virtual Hackaton 2018

The idea behind this project is to extend the repository with monitoring capabilities related to the Alfresco Java API.

Actually it includes the following libraries:

 * DropWizard Metrics (Core, Servlets)
 * Metrics ElasticSearch Reporter provided by Elastic
 
For tracking Java Service you can use two different approaches:

 * adding Monitoring Aspect to contents and set which Behaviors you want to trace
 * overriding the Alfresco Public Services (Java API) adding the related MonitoringMethodInterceptor 

This module is based on an Alfresco Platform JAR Module - SDK 3

To run use `mvn clean install -DskipTests=true alfresco:run` or `./run.sh` and verify that it 

 * Runs the embedded Tomcat + H2 DB 
 * Runs Alfresco Platform (Repository)
 * Runs Alfresco Solr4
 * Packages both as JAR and AMP assembly
 
 Try cloning it, change the port and play with `enableShare`, `enablePlatform` and `enableSolr`. 
 
 Protip: This module will work just fine as a Share module if the files are changed and 
 if the enablePlatform and enableSolr is disabled.
 
# Few things to notice

 * No parent pom
 * WAR assembly is handled by the Alfresco Maven Plugin configuration
 * Standard JAR packaging and layout
 * Works seamlessly with Eclipse and IntelliJ IDEA
 * JRebel for hot reloading, JRebel maven plugin for generating rebel.xml, agent usage: `MAVEN_OPTS=-Xms256m -Xmx1G -agentpath:/home/martin/apps/jrebel/lib/libjrebel64.so`
 * AMP as an assembly
 * [Configurable Run mojo](https://github.com/Alfresco/alfresco-sdk/blob/sdk-3.0/plugins/alfresco-maven-plugin/src/main/java/org/alfresco/maven/plugin/RunMojo.java) in the `alfresco-maven-plugin`
 * No unit testing/functional tests just yet
 * Resources loaded from META-INF
 * Web Fragment (this includes a sample servlet configured via web fragment)
 
# TODO
 
  * Abstract assembly into a dependency so we don't have to ship the assembly in the archetype
  * Purge, 
  * Functional/remote unit tests
   
  
 
