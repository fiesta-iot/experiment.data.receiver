# This is a Sample experiment Server that receives a file from EEE

It is tested to runs on Tomcat successfully

On the experimenter side following has to be done before the deployment
- in the `web.xml` change the location entry for `multipart-config` with the desired location
	```xml
	<multipart-config>
    	<location>LOCATION</location>
	</multipart-config>
	```
- In the `src/eu/fiesta_iot/experimentServer/ExperimentServerService.java` change the `<FILEPATH>` with the desired location.
- Make sure that the `LOCATION` has read-write permissions to the Tomcat user and group (under the name and group Tomcat is running).

- In the Tomcat server change the `conf/content.xml` 
	``` xml
	<Context>
	    ...
	</Context>
	```
	with following 
	``` xml
	<Context allowCasualMultipartParsing="true">
	    ...
	</Context>
	```

- restart Tomcat server

Once the above is done do the following
- `cd <PATH TO EXPERIMENTSERVER>`
- `mvn clean install`
- `cp <PATH TO EXPERIMENTSERVER>/target/ExperimentServer.war <PATH TO TOMCAT WEBAPPS>`