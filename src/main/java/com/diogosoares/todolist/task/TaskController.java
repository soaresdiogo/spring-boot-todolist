package com.diogosoares.todolist.task;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diogosoares.todolist.errors.InvalidTaskDateException;
import com.diogosoares.todolist.errors.TaskNotFoundException;
import com.diogosoares.todolist.errors.UnauthorizedTaskUpdateException;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/")
    public ResponseEntity<Object> create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
        try {
            UUID idUser = (UUID) request.getAttribute("idUser");
            TaskModel task = taskService.createTask(taskModel, idUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(task);
        } catch (InvalidTaskDateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/")
    public List<TaskModel> list(HttpServletRequest request) {
        UUID idUser = (UUID) request.getAttribute("idUser");
        return taskService.listTasksByUser(idUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody TaskModel taskModel, 
                                         HttpServletRequest request, 
                                         @PathVariable UUID id) {
        try {
            UUID idUser = (UUID) request.getAttribute("idUser");
            TaskModel taskUpdated = taskService.updateTask(id, taskModel, idUser);
            return ResponseEntity.ok(taskUpdated);
        } catch (TaskNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (UnauthorizedTaskUpdateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
}