package app.service;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import app.dto.CompleteTodoTaskDTO;
import app.model.TodoTask;
import app.repository.TodoTaskRepository;
import app.util.TaskFlags;

@Service
public class TodoTaskService {

    @Autowired
    TodoTaskRepository todoTaskRepository;

    public ResponseEntity<String> createNewTodoTask(HashMap<String, Object> postedTodoTask, UriComponentsBuilder ucb) {

        String taskName = (String) postedTodoTask.get("taskName");
        String taskDueDate = (String) postedTodoTask.get("taskDueDate");
        String taskDescription = (String) postedTodoTask.get("taskDescription");
        boolean taskIsImportant = (boolean) postedTodoTask.get("taskIsImportant");

        TodoTask newTodoTask = new TodoTask(taskName, taskDueDate, taskDescription, taskIsImportant);

        TodoTask savedTodoTask = todoTaskRepository.save(newTodoTask);

        URI locationOfNewTask = ucb
                .path("tasks/{id}")
                .buildAndExpand(savedTodoTask.getId())
                .toUri();

        return ResponseEntity.created(locationOfNewTask).build();
    }

    public TodoTask findById(Long id) {

        Optional<TodoTask> todoTask = todoTaskRepository.findById(id);

        if (todoTask.isPresent()) {
            return todoTask.get();
        } else {
            return null;
        }
    }

    public Page<TodoTask> findAll(Pageable pageable) {

        Page<TodoTask> page = todoTaskRepository.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.DESC, "id"))));

        return page;
    }

    public ResponseEntity<String> updateTaskFlag(CompleteTodoTaskDTO DTO) {

        if (todoTaskRepository.existsById((long) DTO.getTodoTaskId())) {
            TodoTask changedTodoTask = todoTaskRepository.getReferenceById((long) DTO.getTodoTaskId());

            if (DTO.getFlag().equals(TaskFlags.COMPLETED)) {
                changedTodoTask.setTaskIsCompleted(DTO.getCompleteStatus());
                changedTodoTask.setTaskCompletedDate(LocalDateTime.now());
               
            } else if (DTO.getFlag().equals(TaskFlags.IMPORTANT)) {
                changedTodoTask.setTaskIsImportant(DTO.getCompleteStatus());

            } else if (DTO.getFlag().equals(TaskFlags.DELETED)) {
                changedTodoTask.setTaskIsDeleted(DTO.getCompleteStatus());
                changedTodoTask.setTaskDeletedDate(LocalDateTime.now());

            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

            }

            todoTaskRepository.save(changedTodoTask);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
