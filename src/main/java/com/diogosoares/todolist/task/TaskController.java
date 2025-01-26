package com.diogosoares.todolist.task;

import java.time.LocalDateTime;
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

import com.diogosoares.todolist.utils.Utils;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

  @Autowired
  private ITaskRepository taskRepository;

  @PostMapping("/")
  public ResponseEntity<Object> create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
    var idUser = request.getAttribute("idUser");
    taskModel.setIdUser((UUID) idUser);

    var currentDate = LocalDateTime.now();
    if(currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The start and end date must be greater than the current date");
    }

    if(taskModel.getStartAt().isAfter(taskModel.getEndAt())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The start date must be less than the end date");
    }

    var task = this.taskRepository.save(taskModel);
    return ResponseEntity.status(HttpStatus.OK).body(task);
  }

  @GetMapping("/")
  public List<TaskModel> list(HttpServletRequest request) {
    var idUser = request.getAttribute("idUser");
    var tasks = this.taskRepository.findByIdUser((UUID) idUser);
    return tasks;
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> update(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id) {
    var idUser = request.getAttribute("idUser");

    var task = this.taskRepository.findById(id).orElse(null);
    if(task == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Task not found");
    }
    if(!task.getIdUser().equals(idUser)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This user is not owner of this task");
    }

    Utils.copyNonNullProperties(taskModel, task);
    var taskUpdated = this.taskRepository.save(task);

    return ResponseEntity.status(HttpStatus.OK).body(taskUpdated);
  }
}
