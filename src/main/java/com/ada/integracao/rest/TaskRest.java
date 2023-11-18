package com.ada.integracao.rest;

import com.ada.integracao.Task;
import com.ada.integracao.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/tasks")
public class TaskRest {

    @Autowired
    private TaskService service;

    @PostMapping("/save")
    public ResponseEntity<Task> saveTask(@RequestBody Task task){

        service.saveTask(task);

        return ResponseEntity.ok(task);

    }

    @GetMapping
    public ResponseEntity<Collection<Task>> getAll(){
        return ResponseEntity.ok(service.getAllTasks());
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id){
        service.deleteTask(id);
        return ResponseEntity.ok("");
    }

}
