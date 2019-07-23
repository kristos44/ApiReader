cd to dir where repo cloned

copy StaticFeed-NO.json and DynamicFeed-NO.json to src/test/resources dir

make sure that src/main/resources/app.properties file define proper api addresses and names of two json files previously copied

run mvn assembly:assembly -DdescriptorId=jar-with-dependencies

run java -cp target/ApiClient-1.0-SNAPSHOT-jar-with-dependencies.jar com.mycompany.app.App --apiUrl=http://localhost:9000 --country=NO
with proper api url (required) and country (not required) params set