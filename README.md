# Tasker

## Product Description
This application is a task manager which allows one user to add, view, modify and remove tasks in the form of cards.  

Simply click the 'New' button to usher in an input box, add text and click the 'Submit' button to create a new task. All existing tasks will be presented automatically with their contents kept up-to-date at all times.  

Tasks can have their text changed by clicking on the text, typing or deleting as desired and pressing the 'Enter' key. Tasks can have their status as complete or incomplete toggled by checking the checkbox at the right of each card.  

After toggling a task as complete, a delete button will appear next to the checkbox of the task's card which will erase the task once clicked.  


## Technologies
- PostgreSQL
- Java
- Spring Boot
- Maven
- JUnit
- TypeScript
- React
- Jest
- CSS
- Docker
- OCI Compute


## Application Requirements
You will need to ensure the following variables are set in your system environment:
- HOST (localhost or host IP)
- DB_USER (postgres)
- DB_PASSWORD (secret)
- DB_NAME (task_db)

You will need the following installed to run the application:
- Docker (incl. docker-compose)

That's it! All other requirements ship with the Docker images. 

## Getting Started
Copy the files 'docker-compose.yaml' and 'initdb.sql' located in the root directory of the repository at "https://github.com/nickBarak/tasker-api_spring" to your desired environment.

Now just run the command ```docker-compose up``` and you're good to go!  

Note: If facing issues related to environment variables, set the values in a '.env' file next to 'docker-compose.yaml'.  

If cloning the API repository to your local machine, the API must be packaged manually before running a docker build, like so: ```./mvnw clean verify```. Because the API utilizes dynamic container DNS resolution from docker-compose to identify the database host and datasource autoconfiguration has not been disabled, the host in the database connection string located in the file 'src/main/resources/application.properties' must be switched from 'db' to 'localhost'. You will also need to supply a value for the port. 

When you are ready to run a docker build, switch this value back to 'db' and run a test-free build, like so: ```./mvnw clean package -DskipTests=true``` (This assumes you have installed and configured a PostgreSQL database locally. You can follow the commands listed in the 'initdb.sql' file to initialize the database if test mocking is insufficient).


## UI Route Descriptions

### /
Home (only) page.

## API Route Descriptions

### POST /task
Creates a task and adds it to the database. Requires a JSON body, e.g.:
```json
{
	"id": 1,
	"content": "Task 1",
	"date": "2022-02-16T06:35:25.910Z",
	"isComplete": false
}
```

### GET /task
Gets all of the tasks in the database in JSON format.

### PUT /task/{id}
Updates a task by passing a JSON object with a modified value in the request body. This value may be either 'content' or 'isComplete'.

### DELETE /task/{id}
Deletes a task given the task ID as a path variable in the request URL.  

UI Repository: https://github.com/nickBarak/tasker-ui