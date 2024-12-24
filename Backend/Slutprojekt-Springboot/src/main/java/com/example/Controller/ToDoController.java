package com.example.Controller;

import com.example.Model.ToDoItem;
import com.example.Repository.ToDoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;

@RestController
@RequestMapping("/api/todolist/")

// API
public class ToDoController {
    private final ToDoRepository repository;

    public ToDoController(ToDoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<Collection<ToDoItem>> getAllTasks() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{title}")
    public ResponseEntity<ToDoItem> getTaskByTitle(@PathVariable String title) {
        ToDoItem task = repository.findByTitle(title);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(task);
    }

    @PostMapping
        public ResponseEntity<ToDoItem> addTask(@RequestBody ToDoItem newTask) {
        repository.save(newTask);
        return ResponseEntity.ok(newTask);
    }

    @PutMapping("/{title}")
    public ResponseEntity<ToDoItem> updateTask(@PathVariable String title, @RequestBody ToDoItem updatedTask) {
        ToDoItem existingTask = repository.findByTitle(title);
        if (existingTask == null) {
            return ResponseEntity.notFound().build();
        }
        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        repository.save(existingTask);
        return ResponseEntity.ok(existingTask);
    }

    @DeleteMapping("/{title}")
    public ResponseEntity<Void> deleteTask(@PathVariable String title) {
        ToDoItem task = repository.findByTitle(title);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        repository.delete(title);
        return ResponseEntity.noContent().build();
    }
}
