package app.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import org.mapstruct.Mapper;

import app.util.TaskFlags;

@Mapper(componentModel = "spring")
public class CompleteTodoTaskDTO {

    @NotNull @Positive
    private int todoTaskid;

    @NotNull
    private Boolean completeStatus;

    @NotNull
    private TaskFlags flagToUpdate;

    public CompleteTodoTaskDTO() {}

    public CompleteTodoTaskDTO(TaskFlags flagToUpdate, int todoTaskid, Boolean completeStatus) {
        this.flagToUpdate = flagToUpdate;
        this.todoTaskid = todoTaskid;
        this.completeStatus = completeStatus;
    }

    public int getTodoTaskId() {
        return todoTaskid;
    }
    public void setTodoTaskId(int todoTaskid) {
        this.todoTaskid = todoTaskid;
    }
    public Boolean getCompleteStatus() {
        return completeStatus;
    }
    public void setCompleteStatus(Boolean completeStatus) {
        this.completeStatus = completeStatus;
    }

    public TaskFlags getFlag() {
        return flagToUpdate;
    }

    public void setFlag(TaskFlags flagToUpdate) {
        this.flagToUpdate = flagToUpdate;
    }

}
