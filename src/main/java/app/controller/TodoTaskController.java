package app.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import app.dto.CompleteTodoTaskDTO;
import app.dto.NewTaskDTO;
import app.model.TodoTask;
import app.service.TodoTaskService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TodoTaskController {

    @Autowired
    TodoTaskService todoTaskService;

    @GetMapping("/{id}")
    private ResponseEntity<TodoTask> findById(@PathVariable Long id) {
        TodoTask todoTask = todoTaskService.findById(id);

        if (todoTask != null) {
            return ResponseEntity.ok(todoTask);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    private ResponseEntity<Page<TodoTask>> findAll(Pageable pageable) {

        Page<TodoTask> todoTaskPage = todoTaskService.findAll(pageable);

        return ResponseEntity.ok(todoTaskPage);
    }

    @PatchMapping("/flagupdate")
    private ResponseEntity<String> updateCompletedFlag(@Valid @RequestBody CompleteTodoTaskDTO DTO) {
        return todoTaskService.updateTaskFlag(DTO);
    }

    @PostMapping
    private ResponseEntity<String> createNewTask(@Valid @RequestBody NewTaskDTO DTO,
            UriComponentsBuilder ucb) {
        return todoTaskService.createNewTodoTask(DTO, ucb);
    }

}
