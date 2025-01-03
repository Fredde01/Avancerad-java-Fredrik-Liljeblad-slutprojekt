# To-Do List Management System 
## This project was created as a school assignment. What I have created is an application that includes a To-Do list design that will communicate with an api that will then retrieve information from the backend and return it to the frontend (to the list).

## The goal with the project

* Getting the frontend and backend to communicate 
* Create an api (GET, POST, PUT, DELETE)
* Build a project with, for example Springboot and JavaFX
* Creating a userfriendly design
* Being able to store the tasks without a database 

#### 


## Functions 

* Add a task to the list 
* Edit a task from the list 
* Delete a task from the list
* Show all tasks 
* Store the tasks as long as the server is running

--- 

## How is the project structured?
#### Frontend - The frontend design is built with Javafx and scenebuilder. I really appreciated using scenebuilder because it made things a lot easier, for example being able to drag buttons directly to where I wanted. But basically the frontend design includes:

* Buttons (Add task, Edit task, Delete task, Save task)
* Textfields (Write new task, Write title to get task)
* Listview (For the user to se all the tasks)
* Styling (Colors, border, height/width)

#### Backend - The backend was built with Springboot it worked really well with creating the api. I created a class toDoItem where i defined the variables that I wanted to add to the To-Do list. Then I created a repository class with the hashmap and some methods for handling data and storing, and lastly the controller class for the api. The backend included methods for api/classes:

* GET: getAllTasks() - Returns a list with all stored tasks
* GET: getTaskByTitle(@PathVariable String title) - Returns the specific task based on title
* POST: addTask(@RequestBody ToDoItem newTask) - Adds a new task 
* PUT: updateTask(@PathVariable String title, @RequestBody ToDoItem updatedTask) - Saves the updated task
* DELETE: deleteTask(@PathVariable String title) - Deletes a specific task

* Interface class "RepositoryInterface" - for the datastoring class to implement ceratin methods
* Abstract class "BaseItem" - easier inheritance for classes that needs the same attributes, in this case ToDoItem extends BaseItem






## How to run the program
* Should have the latest version of JAVA and some kind of IDE

1. Clone the reposity to the folder you like
2. Open the frontend and backend in two seperate windows 
3. Run the backend server
4. Run the frontend app
5. Now everything should work!
