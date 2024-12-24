package com.example.slutprojektjavafx;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class MyController {
    ObservableList<String> tasks = FXCollections.observableArrayList();

    private String readResponse(HttpURLConnection connection) throws IOException {
        BufferedReader reader;
        if(connection.getResponseCode() >=200 && connection.getResponseCode() < 300) {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } else {
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        return response.toString();
    }

    @FXML
    private TextField getTaskInfo;

    @FXML
    private TextField antTextField;

    @FXML
    private TextField dtTextField;

    @FXML
    private TextField etTextField;

    @FXML
    private ListView<String> listView;

    @FXML
    void addNewTask(ActionEvent event) {
        String taskInput = antTextField.getText();

        if (taskInput != null && !taskInput.trim().isEmpty()) {

            String[] taskParts = taskInput.split("-", 2);

            if (taskParts.length == 2) {
                String taskTitle = taskParts[0].trim();
                String taskDescription = taskParts[1].trim();

                ToDoItem newTask = new ToDoItem(taskTitle, taskDescription);

                ObjectMapper objectMapper = new ObjectMapper();
                try {

                    String json = objectMapper.writeValueAsString(newTask);

                    URL url = new URL("http://localhost:8080/api/todolist/");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setRequestProperty("Content-Type", "application/json");

                    connection.getOutputStream().write(json.getBytes());

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        System.out.println("Task added");
                    } else {
                        System.out.println("Failed to add task, Response Code: " + responseCode);
                    }

                    String taskDisplayText = taskTitle + " - " + taskDescription;
                    tasks.add(taskDisplayText);
                    listView.setItems(tasks);

                    antTextField.clear();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Invalid input format. Please use 'title - description'");
            }
        } else {
            System.out.println("Please enter a valid task");
        }
    }

    @FXML
    void deleteTask(ActionEvent event) {
        String idToDelete = dtTextField.getText();

        if (idToDelete != null && !idToDelete.trim().isEmpty()) {
            try {
                URL url = new URL("http://localhost:8080/api/todolist/" + idToDelete);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("DELETE");

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                    System.out.println("Task deleted successfully.");
                    dtTextField.clear();
                    showAllTasks(null);
                } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                    System.out.println("Task not found.");
                } else {
                    System.out.println("Failed to delete task. Response code: " + responseCode);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Please enter a valid title.");
        }
    }

    private String taskDescriptionFromResponse(String response) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ToDoItem task = objectMapper.readValue(response, ToDoItem.class);
            return task.getDescription();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error";
        }
    }

    @FXML
    void editTask(ActionEvent event) {
        String taskTitle = etTextField.getText();

        if (taskTitle != null && !taskTitle.trim().isEmpty()) {
            try {

                URL url = new URL("http://localhost:8080/api/todolist/" + taskTitle);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");


                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    String response = readResponse(connection);


                    ObjectMapper mapper = new ObjectMapper();
                    ToDoItem task = mapper.readValue(response, ToDoItem.class);


                    getTaskInfo.setText(task.getDescription());
                } else {
                    getTaskInfo.setText("Task not found!");
                }
            } catch (IOException e) {
                e.printStackTrace();
                getTaskInfo.setText("Error retrieving task.");
            }
        } else {
            getTaskInfo.setText("Please enter a valid title.");
        }
    }

    @FXML
    void saveTask(ActionEvent event) {
        String taskTitle = etTextField.getText();
        String updatedDescription = getTaskInfo.getText();

        if (taskTitle != null && !taskTitle.trim().isEmpty() && updatedDescription != null && !updatedDescription.trim().isEmpty()) {
            try {
                URL url = new URL("http://localhost:8080/api/todolist/" + taskTitle);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("PUT");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");

                ToDoItem updatedTask = new ToDoItem();
                updatedTask.setTitle(taskTitle);
                updatedTask.setDescription(updatedDescription);

                getTaskInfo.clear();

                ObjectMapper objectMapper = new ObjectMapper();
                String json = objectMapper.writeValueAsString(updatedTask);

                connection.getOutputStream().write(json.getBytes());

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    System.out.println("Task updated successfully.");


                } else {
                    System.out.println("Failed to update task. Response code: " + responseCode);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Please provide valid input.");
        }
    }

    @FXML
    void showAllTasks(ActionEvent event) {
        try {
            URL url = new URL("http://localhost:8080/api/todolist/");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            ObjectMapper objectMapper = new ObjectMapper();
            List<ToDoItem> taskList = objectMapper.readValue(connection.getInputStream(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, ToDoItem.class));

            tasks.clear();
            for (ToDoItem task : taskList) {
                tasks.add(task.getTitle() + " - " + task.getDescription());
            }
            listView.setItems(tasks);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
