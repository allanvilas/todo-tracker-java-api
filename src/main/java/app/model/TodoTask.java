package app.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "todo_task")
public class TodoTask {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

        @Column(nullable = false)
    String TaskName;

    @Column(nullable = false)
    LocalDateTime TaskDueDate;
    String TaskDescription;
    LocalDateTime TaskCreatedDate;
    LocalDateTime TaskUpdateDate = null;
    Boolean TaskIsImportant = false;
    Boolean TaskIsDeleted = false;
    LocalDateTime TaskDeletedDate = null;
    Boolean TaskIsCompleted = null;
    LocalDateTime TaskCompletedDate = null;

    public TodoTask(String taskName, LocalDateTime taskDueDate, String taskDescription, Boolean taskIsImportant) {
        TaskName = taskName;
        TaskDueDate = taskDueDate;
        TaskDescription = taskDescription;
        TaskIsImportant = taskIsImportant;
    }

    public String getTaskName() {
        return TaskName;
    }

    public void setTaskName(String taskName) {
        TaskName = taskName;
    }

    public LocalDateTime getTaskDueDate() {
        return TaskDueDate;
    }

    public void setTaskDueDate(LocalDateTime taskDueDate) {
        TaskDueDate = taskDueDate;
    }

    public String getTaskDescription() {
        return TaskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        TaskDescription = taskDescription;
    }

    public LocalDateTime getTaskCreatedDate() {
        return TaskCreatedDate;
    }

    public void setTaskCreatedDate(LocalDateTime taskCreatedDate) {
        TaskCreatedDate = taskCreatedDate;
    }

    public LocalDateTime getTaskUpdateDate() {
        return TaskUpdateDate;
    }

    public void setTaskUpdateDate(LocalDateTime taskUpdateDate) {
        TaskUpdateDate = taskUpdateDate;
    }

    public Boolean getTaskIsImportant() {
        return TaskIsImportant;
    }

    public void setTaskIsImportant(Boolean taskIsImportant) {
        TaskIsImportant = taskIsImportant;
    }

    public Boolean getTaskIsDeleted() {
        return TaskIsDeleted;
    }

    public void setTaskIsDeleted(Boolean taskIsDeleted) {
        TaskIsDeleted = taskIsDeleted;
    }

    public LocalDateTime getTaskDeletedDate() {
        return TaskDeletedDate;
    }

    public void setTaskDeletedDate(LocalDateTime taskDeletedDate) {
        TaskDeletedDate = taskDeletedDate;
    }

    public Boolean getTaskIsCompleted() {
        return TaskIsCompleted;
    }

    public void setTaskIsCompleted(Boolean taskIsCompleted) {
        TaskIsCompleted = taskIsCompleted;
    }

    public LocalDateTime getTaskCompletedDate() {
        return TaskCompletedDate;
    }

    public void setTaskCompletedDate(LocalDateTime taskCompletedDate) {
        TaskCompletedDate = taskCompletedDate;
    }

    @PrePersist void onCreate() {
        TaskCreatedDate = LocalDateTime.now();
    }
    
    @PreUpdate void onUpdate() {
        TaskCreatedDate = LocalDateTime.now();
    }

}
