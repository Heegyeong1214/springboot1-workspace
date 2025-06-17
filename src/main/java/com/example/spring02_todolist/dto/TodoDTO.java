package com.example.spring02_todolist.dto;

import com.example.spring02_todolist.entity.TodoEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class TodoDTO {
	private int id;
	private int completed;
	private String todoname;
	
	//TodoDTO를 Entity로 바꾸는 작업
	public TodoEntity toEntity() {
		return TodoEntity.builder()
				.id(id)
				.completed(completed)
				.todoname(todoname)
				.build();
	}
	
	//TodoEntity => todoDTO (Entity로 되어있는 것을 받아오기)
	//static인 이유는  (Entity로 되어있는 것을 받아오기 때문
	public static TodoDTO toDTO(TodoEntity todoEntity) {
		return TodoDTO.builder()
				.id(todoEntity.getId())
				.completed(todoEntity.getCompleted())
				.todoname(todoEntity.getTodoname())
				.build();
	}
	
}
