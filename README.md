# Tasker

## Product Description
This application is a task manager which allows one user to add, view, modify and remove tasks in the form of cards.  

Simply click the 'New' button to usher in an input box, add text and click the 'Submit' button to create a new task. All existing tasks will be presented automatically with their contents kept up-to-date at all times.  

Tasks can have their text changed by clicking on the text, typing or deleting as desired and pressing the 'Enter' key. Tasks can have their status as complete or incomplete toggled by checking the checkbox at the right of each card.  

After toggling a task as complete, a delete button will appear next to the checkbox of the task's card which will erase the task once clicked.  


## Technologies
- Testing  
	- Cucumber
	- JUnit
	- Mockito
	- Jest
	- React Testing Library
- Back-End  
	- PostgreSQL
	- Hibernate
	- Java
	- Spring Boot
	- Maven
- Front-End  
	- TypeScript
	- React
	- Redux
	- React Router
	- CSS
- Operations  
	- Git
	- Docker
	- Jenkins
	- Linux
	- OCI


## Application Requirements
You will need to ensure the following variables are set in your system environment:
- DB_USER (postgres)
- DB_PASSWORD (secret)
- JWT_SECRET (secret_key)

You will need the following installed to run the application:
- Docker (incl. docker-compose)

That's it! All other requirements ship with the Docker images. 

## Getting Started
Copy the files 'docker-compose.yaml' and 'initdb.sql' located in the root directory of the repository at "https://github.com/nickBarak/tasker-api_spring" to your desired environment.

Now just run the command ```docker-compose up``` and you're good to go!  

Note: If facing issues related to environment variables, set the values in a '.env' file in the execution directory.  


## UI Route Descriptions

### / (USER, ADMIN)
Home page, displays all tasks of authenticated user.  

### /login (ANY)
Displays forms to log in or sign up

### /admin (ADMIN)
Displays all users in the database and allows modification and deleting of users.

## API Route Descriptions

### POST /login/register (ANY)
Creates a user and adds it to the database. Requires a JSON body, e.g.:
```json
{
	"username": "user",
	"password": "pass",
}
```  

### POST /login (ANY)
Authenticates a user
```json
{
	"username": "user",
	"password": "pass",
}
```  


### POST /task (USER, ADMIN)
Creates a task and adds it to the database. Requires a JSON body, e.g.:
```json
{
	"id": 1,
	"content": "Task 1",
	"date": "2022-02-16T06:35:25.910Z",
	"isComplete": false
}
```

### GET /task (USER, ADMIN)
Gets all of the tasks in the database in JSON format.

### GET /task/{id} (USER, ADMIN)
Gets one task

### PUT /task/{id} (USER, ADMIN)
Updates a task by passing a JSON object with a modified value in the request body. This value may be either 'content' or 'isComplete'.

### DELETE /task/{id} (USER, ADMIN)
Deletes a task given the task ID as a path variable in the request URL.  


### GET /user (ADMIN)
Gets all of the tasks in the database in JSON format.

### GET /user/{id} (ADMIN)
Gets one user

### PUT /user/{id} (ADMIN)
Updates a task by passing a JSON object with a modified value in the request body. This value may be either 'content' or 'isComplete'.

### DELETE /user/{id} (ADMIN)
Deletes a task given the task ID as a path variable in the request URL.  


### GET /error (ANY)
Error page listing status code, request URI, exception type and message

API Repository: https://github.com/nickBarak/tasker-api_spring
UI Repository: https://github.com/nickBarak/tasker-ui