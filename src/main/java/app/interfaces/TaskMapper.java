package app.interfaces;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import app.dto.NewTaskDTO;
import app.model.TodoTask;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(source = "taskName", target = "name")
    @Mapping(source = "taskDescription", target = "desc")
    @Mapping(source = "taskDueDate", target = "dueDate")
    @Mapping(source = "taskIsCompleted", target = "isCompleted")
    @Mapping(source = "taskIsImportant", target = "isImportant")
    NewTaskDTO newTaskDTO(TodoTask todoTask);
    
    @Mapping( source = "name", target = "taskName")
    @Mapping( source = "desc", target = "taskDescription")
    @Mapping( source = "dueDate", target = "taskDueDate")
    @Mapping( source = "isCompleted", target = "taskIsCompleted")
    @Mapping( source = "isImportant", target = "taskIsImportant")
    // Ignored properties that is managed by the API
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "taskCompletedDate", ignore = true)
    @Mapping(target = "taskDeletedDate", ignore = true)
    @Mapping(target = "taskUpdateDate", ignore = true)
    @Mapping(target = "taskCreatedDate", ignore = true)
    @Mapping(target = "taskIsDeleted", ignore = true)
    TodoTask todoTask(NewTaskDTO newTaskDTO);

}
