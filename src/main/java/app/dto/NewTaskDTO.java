package app.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class NewTaskDTO {
    @NotNull @NotBlank @NotEmpty
    private String name;
    private String desc = "";
    @NotNull
    private LocalDateTime dueDate;
    private Boolean isImportant = false;
    private Boolean isCompleted = false;
    private LocalDateTime completedDate = null;

    public NewTaskDTO() {}
    
    public NewTaskDTO(@NotNull @NotBlank @NotEmpty String name, String desc, @NotNull String dueDate,
            Boolean isImportant, Boolean isCompleted, String completedDate) {
        this.name = name;
        this.desc = desc;
        this.dueDate = LocalDateTime.parse(dueDate,DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        this.isImportant = isImportant;
        this.isCompleted = isCompleted;
        this.completedDate = LocalDateTime.parse(completedDate,DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public NewTaskDTO(@NotNull @NotBlank @NotEmpty String name, String desc, @NotNull String dueDate,
    Boolean isImportant) {
        this.name = name;
        this.desc = desc;
        this.dueDate = LocalDateTime.parse(dueDate,DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        this.isImportant = isImportant;
    }

    public NewTaskDTO(@NotNull @NotBlank @NotEmpty String name, String desc, @NotNull String dueDate) {
        this.name = name;
        this.desc = desc;
        this.dueDate = LocalDateTime.parse(dueDate,DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public LocalDateTime getDueDate() {
        return dueDate;
    }
    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
    public Boolean getIsImportant() {
        return isImportant;
    }
    public void setIsImportant(Boolean isImportant) {
        this.isImportant = isImportant;
    }
    public Boolean getIsCompleted() {
        return isCompleted;
    }
    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

}
