package app.service;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import app.dto.CompleteTodoTaskDTO;
import app.dto.NewTaskDTO;
import app.interfaces.ITaskMapper;
import app.model.TodoTask;
import app.repository.TodoTaskRepository;
import app.util.TaskFlags;
import jakarta.validation.Valid;

/**
 * Service for managing TodoTask operations.
 */
@Service
public class TodoTaskService {

    @Autowired
    TodoTaskRepository todoTaskRepository;
    private final ITaskMapper taskMapper;

    @Autowired
    public TodoTaskService(ITaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    /**
     * Creates a new TodoTask.
     *
     * @param DTO Data Transfer Object containing details of the task to be created.
     * @param ucb UriComponentsBuilder to build the URI of the created task.
     * @return ResponseEntity with the location of the newly created task.
     */
    public ResponseEntity<String> createNewTodoTask(@Valid @RequestBody NewTaskDTO DTO, UriComponentsBuilder ucb) {

        TodoTask newTodoTask = taskMapper.todoTask(DTO);

        TodoTask savedTodoTask = todoTaskRepository.save(newTodoTask);

        URI locationOfNewTask = ucb
                .path("tasks/{id}")
                .buildAndExpand(savedTodoTask.getId())
                .toUri();

        return ResponseEntity.created(locationOfNewTask).build();
    }

    /**
     * Finds a TodoTask by its ID.
     *
     * @param id The ID of the task to find.
     * @return The TodoTask if found, otherwise null.
     */
    public TodoTask findById(Long id) {

        Optional<TodoTask> todoTask = todoTaskRepository.findById(id);

        if (todoTask.isPresent()) {
            return todoTask.get();
        } else {
            return null;
        }
    }

    /**
     * Finds all TodoTasks with pagination.
     *
     * @param pageable Pagination information.
     * @return A page of TodoTasks.
     */
    public Page<TodoTask> findAll(Pageable pageable) {

        Page<TodoTask> page = todoTaskRepository.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.DESC, "id"))));

        return page;
    }

    /**
     * Updates the flag of an existing TodoTask.
     *
     * @param DTO Data Transfer Object containing the task ID and the new flag status.
     * @return ResponseEntity indicating the result of the update operation.
     */
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
