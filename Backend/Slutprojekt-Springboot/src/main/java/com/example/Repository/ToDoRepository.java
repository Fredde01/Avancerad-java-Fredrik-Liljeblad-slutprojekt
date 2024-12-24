package com.example.Repository;

import com.example.Model.ToDoItem;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;

//Storing and handling data
@Repository
public class ToDoRepository {
    private final HashMap<String, ToDoItem> todoItems = new HashMap<>();

    public Collection<ToDoItem> findAll() {
        return todoItems.values();
    }
    public ToDoItem findByTitle(String title) {
        return todoItems.get(title);
    }
    public void save(ToDoItem task) {
        todoItems.put(task.getTitle(), task);
    }
    public void delete(String title) {
        todoItems.remove(title);
    }

}
