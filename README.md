# ProjectProfectusCC
* **dbScripts** - Scripts to create database in PostgreSQL
* **pccREST** - REST API developed using Java, Spring Boot & JPA
* **pcc-web** - Front-end app developed using React

# Business logic and Assumptions
For a Product 
	there is only one purchase price
	multiple purchases can be made with the same purchase price
	multiple sales can be made with the different sale price
	prices are assumed as price per an item
	
# Seup instructions
* Create database using the script (./dbScripts/profectusdb.sql)
* If the database is not in localhost, change the localhost to new host in the properties file (./pccREST/src/main/resources/application.properties)
* Build the Java project ( pccREST ) from ./pccREST using "mvn package"
* cd to "target" directory and run "java -jar pccREST-0.0.1-SNAPSHOT.jar", this will be listening on port 8082 (this can be changed in the properties file
* Try this http://localhost:8082/ in a browser to check if it is working 
* Build the React project (pcc-web) from ./pcc-web using "npm run-script build" (API call url ./pcc-web/src/config)
* cd to build directory and run live-server --port=3000
* Try this http://localhost:3000/ to access the app

# Feedback
* how long did it take?
*	took me about 30 hours
* are the instruction clear?
*	yes clear, except the purchase price, I was confused about the price if its is for total items or one item
* would you make any modifications?
*	no this sort of challenge, it is appropriate
* how was your experience?
*	very good, but it took longer than I expected
