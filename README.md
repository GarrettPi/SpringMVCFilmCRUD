## Spring MVC Film C.R.U.D.

### Week 8 Team Project for Skill Distillery

### Overview

This week's project was implementing a full web application with the use Create, Read, Update, and Delete queries from our film database (MAMP via MySQL server) that was introduced previously accessed through the JDBC api.  From the dynamic web page we created, a user is able to perform a variety of tasks such as search for films by ID, patterns or keywords, and add, edit, or delete films. Additionally, a user can add an actor as well as add them to a film. Should s/he encounter errors they receive an appropriate error message. 

Using the Data Access Object (DAO) pattern we we able to separate the data from our MySql database while maintaining the integrity of our database.  DELETE queries were careful to include the use of a Where clause to ensure that only the expected results were actually deleted. Through the use of a controller, database data was retrieved by the DAO and passed to corresponding JSPs for viewing via the webpage. Since the controller has an instance of the Film DAO it is able to call its respective methods and access database data.  This corresponding data is integrated into Model and View Objects that were provided to our JSPs.     

### How to Use

Open the project in your preferred IDE and run on a server also of your choice.  This project has been tested in Spring Tool Suite 4 with the Apache Tomcat v. 8.5 acting as our web server.  Google Chrome was utilized as our web browser as well with the preference set in Tomcat to run on an external browser.  

### Technologies Used

* Java 
* Apache Tomcat 8.5
* Spring MVC 
* MySQL 
* JDBC
* Gradle
* ERD
* MAMP
* Kanban board

### Lessons Learned

We refined our learning of SQL syntax with statements for INSERT, UPDATE, and DELETE queries.  Additionally, we learned how to construct the controller class and how the information interacted between our persistence (database) layer and the webpage which is viewed by the user.  Our understanding of JSPs which were introduced this week increased as we were able to more clearly see how they were able to manipulate and display our data.  Even with trying to be extremely careful and overcommunicating what we were doing we ran into merge conflict issues; however, we were able to troubleshoot together and ensure that our project's integrity was not compromised.  Use of a kanban board in Trello helped us stay organized and ensure that our project's requirements were completed.  


