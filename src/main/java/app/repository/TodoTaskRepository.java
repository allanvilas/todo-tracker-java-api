package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.model.TodoTask;

public interface TodoTaskRepository extends JpaRepository<TodoTask, Long> {

}
