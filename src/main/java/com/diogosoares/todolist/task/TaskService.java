package com.diogosoares.todolist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diogosoares.todolist.errors.InvalidTaskDateException;
import com.diogosoares.todolist.errors.TaskNotFoundException;
import com.diogosoares.todolist.errors.UnauthorizedTaskUpdateException;
import com.diogosoares.todolist.utils.Utils;

@Service
public class TaskService {

    @Autowired
    private ITaskRepository taskRepository;

    public TaskModel createTask(TaskModel taskModel, UUID idUser) {
        LocalDateTime currentDate = LocalDateTime.now();
        if (currentDate.isAfter(taskModel.getStartAt()) || 
            currentDate.isAfter(taskModel.getEndAt())) {
            throw new InvalidTaskDateException("Start and end dates must be in the future");
        }

        if (taskModel.getStartAt().isAfter(taskModel.getEndAt())) {
            throw new InvalidTaskDateException("Start date must be before end date");
        }

        taskModel.setIdUser(idUser);
        return taskRepository.save(taskModel);
    }

    public List<TaskModel> listTasksByUser(UUID idUser) {
        return taskRepository.findByIdUser(idUser);
    }

    public TaskModel updateTask(UUID taskId, TaskModel taskModel, UUID idUser) {
        TaskModel existingTask = taskRepository.findById(taskId)
            .orElseThrow(() -> new TaskNotFoundException("Task not found"));

        if (!existingTask.getIdUser().equals(idUser)) {
            throw new UnauthorizedTaskUpdateException("User is not the owner of this task");
        }

        Utils.copyNonNullProperties(taskModel, existingTask);
        return taskRepository.save(existingTask);
    }
}