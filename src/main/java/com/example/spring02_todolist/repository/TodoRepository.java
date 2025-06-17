package com.example.spring02_todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spring02_todolist.entity.TodoEntity;

@Repository// 레포지토리로 사용할거라서 어노테이션으로 선언
//<Entity, id의 객체타입(클래스)> 넣어야 한다!
public interface TodoRepository extends JpaRepository<TodoEntity, Integer> {

}

/*
JPA는 인터페이스로서 자바 표준 명세서입니다. 인터페이스인 JPA를 사용하기 위해서는 구현체가 필요한데,
대표적으로 Hibernate, Eclipse Link 등이 있습니다.
하지만 Spring에서는 JPA를 사용할 때 이 구현체들을 직접 다루지 않고,
구현체들을 좀 더 쉽게 사용하고자 추상화시킨 Spring Data JPA라는 모듈을 이용하여 JPA 기술을 다룹니다.
spring-boot-starter-data-jpa : 스프링 부트용 Spring Data Jpa 추상화 라이브러리를 추가한다.
*/

