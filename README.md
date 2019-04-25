# CompassAPI

Instructions to run the API. For now the server is already deployed on a linux server provided by the sponsor.

In order to run the API you would need:
- Java (JRE above or equal 1.8)[https://www.oracle.com/technetwork/java/javaee/downloads/jdk8-downloads-2133151.html]
- MongoDB [https://docs.mongodb.com/manual/administration/install-community/]
- Apache ant [https://ant.apache.org/]
- apidoc node module [https://www.npmjs.com/package/apidoc]
(Install Node package manager and run "npm install -g apidoc" command in terminal. 
NOTE: Create an environment varibale for node modules before running above npm command.)

First step is to set up database.
- Start the mongo daemon process.
- Go to location "Sample Databases/healdb"
- Open terminal and run : 
      mongorestore -d <DB_NAME> .
- It will create a database named as "DB_NAME".
- You have to put the same db name under dao.properties for parameter mongo.uri and mongo.dbname, located under src/edu/asu/heal/core/api/dao/ .

Once the DB is all set up, you can do the below steps to build and deploy the API on tomcat server :
- Set tomcat_webapps property in build.properties file to point your tomcat/webapps location.
- Open terminal and run : 
        ant clean
        ant build
        ant deploy
 
The API documentation can be found under apidocs/index.html.

Once this process is finished, you should find the WAR file of the API deployed in `tomcat_webapps`.

The context name for the API is CompassAPI. Once the war file is deployed on a server, you can access endpoints of the API using
`https://{server_address}}/CompassAPI/rest/{endpoint_name}`
