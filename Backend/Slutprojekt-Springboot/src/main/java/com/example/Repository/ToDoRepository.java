package com.example.Repository;

import com.example.Model.ToDoItem;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;

//Storing and handling data
@Repository
public class ToDoRepository implements RepositoryInterface{
    private final HashMap<String, ToDoItem> todoItems = new HashMap<>();

    @Override
    public Collection<ToDoItem> findAll() {
        return todoItems.values();
    }
    @Override
    public ToDoItem findByTitle(String title) {
        return todoItems.get(title);
    }
    @Override
    public void save(ToDoItem task) {
        todoItems.put(task.getTitle(), task);
    }
    @Override
    public void delete(String title) {
        todoItems.remove(title);
    }

}
