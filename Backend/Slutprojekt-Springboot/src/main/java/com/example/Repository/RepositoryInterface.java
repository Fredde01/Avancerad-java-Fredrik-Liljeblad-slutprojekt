package com.example.Repository;

import com.example.Model.ToDoItem;
import java.util.Collection;

public interface RepositoryInterface {
    Collection<ToDoItem> findAll();
    ToDoItem findByTitle(String title);
    void save(ToDoItem task);
    void delete(String title);

}
