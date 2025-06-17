package com.example.spring02_todolist.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.spring02_todolist.dto.TodoDTO;
import com.example.spring02_todolist.entity.TodoEntity;
import com.example.spring02_todolist.repository.TodoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service //서비스를 처리해줄 거기 때문에 어노테이션으로 정의해줌
public class TodoServiceImpl implements TodoService {
	
	 // 빈은 그냥 선언되어 있음 -> 그냥 객체라고 생각해놓으면 됨
	//스프링이 자동으로 만들어 둔 빈 객체를 주입해줌
	//톰킷이 켜지면 빈이 스프링 컨테이너라는 곳에 객체가 생성됨
	//생성된 빈(객체)가 이미 선언되어 있는 클래스에서 사용할 수 있도록 하는 것
	//Repository로 하면 new라고 다시 객체를 생성해서 자동으로 저장해놓음
	@Autowired
	private TodoRepository todoRepository;
	
	public TodoServiceImpl() {
		
	}

	@Transactional(readOnly = true) // 읽기만 할거야~~
	@Override
	public List<TodoDTO> search() throws Exception {
		//findALL() : 원래는 JDBC 처럼 select* from todotbl: -> 실행시켜줌 -> 그 결과를 rs로 받아줌.
		//그 rs를 DTO에 담고 List에 추가해주는 작업들을 알아서 자동적으로 다 해줌
		//근데 TodoEntity로 옴: JPaRepsitory<TodoEntity, Integer>로 선언해두었기 때문에 List에 TodoEntity를 넣어줌
		//Entity로 온 걸 TodoDTO로 넘겨주어야 함
		
		
		// stream(): 리스트를 스트림으로 변환하여 각 요소에 map()을 적용
	    // map(TodoDTO::toDTO): 각 TodoEntity를 TodoDTO로 변환 (정적 메서드 toDTO 사용)
	    // collect(Collectors.toList()): 변환된 요소들을 다시 리스트로 수집

		List<TodoEntity> listTodoEntity = todoRepository.findAll();  //Entity를 받아온 것을
		List<TodoDTO> listTodoDTO = listTodoEntity.stream() // 
				                    .map(TodoDTO :: toDTO) //클래스 안에 있는 메소드 실행할 때: Entity를 DTO로 변경해서 리턴하는 메소드 실행
				                    .collect(Collectors.toList()); //List로 넘겨줌
		//stream을 읽어와서 각항목에 있는 걸 map, collect를 활용해서 List로 바꿔주기
		return listTodoDTO;
	}

	@Transactional(rollbackFor = Exception.class)//insert하다가 오류가 생기면 rollback할거야
	@Override
	public void insert(TodoDTO dto) throws Exception {
		// TODO Auto-generated method stub
		
		
		//dto로 받은 것을 entity로 바꿔줘야 하니까! 그리고 난 다음에 Service로 넘겨줌
		
		TodoEntity insert = todoRepository.save(dto.toEntity());
		log.info("insert=>{}", insert);
		
		//예외 발생시 테스트용 코드 (주석 해제시 록백확인 가능)
		//예외가 발생될 떄 roll back하기
//		if( true) 
//			throw new Exception("강제 예외 발생");
		
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void update(TodoDTO dto) throws Exception {
		//DTO를 Entity로 바꿔서 넘겨줘야지. 
		TodoEntity update = todoRepository.save(dto.toEntity());
		log.info("update=>{}", update);
		
		//예외 발생시 테스트용 코드 (주석 해제시 록백확인 가능)
		//예외가 발생될 떄 roll back하기
//		if(true) 
//			throw new Exception("강제 예외 발생");
		
		
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void delete(int id) throws Exception {
			todoRepository.deleteById(id);
	}

	
}
