package com.example.spring02_todolist.controller;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PutExchange;

import com.example.spring02_todolist.dto.TodoDTO;
import com.example.spring02_todolist.service.TodoService;

import lombok.extern.slf4j.Slf4j;

/*
[TodoTbl API URI 설계]
조회   /todo/all                 GET
등록   /todo                     POST
수정   /todo                     PUT
삭제   /todo/{id}                DELETE
*/


/*
 [HTTP 상태 코드]
  상황	                            코드	        의미
내부 서버 오류 (예: 롤백된 DB 트랜잭션)	500	        Internal Server Error
클라이언트 잘못 (필드 누락 등)	        400	        Bad Request
요청한 자원이 없음 (삭제 대상 없음 등)	404	        Not Found
 */


//다른 도메인에 대한 접근 허용
//@CrossOrigin(origins= {"http://127.0.0.1:3000", "http://172.16.133.6"})
@CrossOrigin("*")


@Slf4j
@RestController
public class TodoController {
	
	//DI(Dependency Injection) : 의존성 주입
	//객체를 이미 생성하고 필요할 때 주입시켜주는 방식
	
	//서비스에 있는 메소드를 사용해야하기 때문에 서비스에 있는 객체를 가지고 와야한다.
	//참조할 때는 클래스가 아닌 인터페이스를 선언함 -> 상위에 있는 인터페이스를 사용하면 그 아래에 있는 클래스를 일일이 명명하지 않아도 상관 없지롱
	
	@Autowired
	private TodoService todoService;

	
	//인자 값이 없는 생성자 정의해두기
	public TodoController() {

	}
	
	
	@GetMapping(value="/todo/all") //http://localhost:8090/todo/all
	//데이터 받을 때 잘못 받는 경우가 있을 수 있으니 throws 사용?
	public ResponseEntity<List<TodoDTO>> getList() throws Exception{
	//log.info("getList() => {}", todoService.search());
	
	//생성자 패턴 이용해서 
	//ResponseEntity<List<TodoDTO>>를 담아서 브라우저로 넘김
	//DTO에 있는 것은 객체로, List로 되어있는 것은 배열 형태로 바뀌어서 리턴이됨 
	//return new ResponseEntity<List<TodoDTO>>(todoService.search(),HttpStatus.OK);
		
	//빌터 패턴
		//return ResponseEntity.ok(todoService.search());
		return ResponseEntity.ok().body(todoService.search());
	}
	
	//{"completed":0, "todoname": "잠자기"}
	@PostMapping(value="/todo")
	//JSON은 @RequestBody를 적용
	public ResponseEntity<HashMap<String, String>> postTodo(@RequestBody TodoDTO dto){ //throws Exception{
		//서비스를 호출하면서 넘겨야 한다
		try{ todoService.insert(dto);
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("createDate", new Date().toString());
		map.put("message", "Insert OK");
		
		//Header에 담아서 전달할 때
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application","json",Charset.forName("UTF-8")));
		return ResponseEntity.ok().headers(headers).body(map);
		//return new ResponseEntity<HashMap<String,String>>(map, HttpStatus.OK);
		}catch(Exception e) {
			HashMap<String, String> errorMap = new HashMap<>();
			errorMap.put("createDate", new Date().toString());
			errorMap.put("message", "등록 실패" + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMap);
		}
	
}
	
	
	@PutMapping(value="/todo")
	//Table에서 셀렉해서 넘겨준 값이 dto(체크가 되어서 completed가 1인 시퀀스를 가지고 옴)
	public ResponseEntity<Map<String, String>> putTodo(@RequestBody TodoDTO dto){
		Map<String, String> response = new HashMap<>();
		try {
			dto.setCompleted(dto.getCompleted() == 0? 1 : 0);
			todoService.update(dto);
			
			response.put("status", "success");
			response.put("id", String.valueOf(dto.getId())); //바꾼 completed에 해당되는 id를 보고 싶어
			return ResponseEntity.ok(response);
		}catch(Exception e){
			log.error("수정 중 예외 발생:{}", e.getMessage(),e);
			response.put("status", "error");
			response.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		
	}
	//http://localhost:8090/todo/2
	@DeleteMapping(value="/todo/{id}")
	public ResponseEntity<Void> deleteTodo(@PathVariable(name="id") int id){
		try{todoService.delete(id);
		// RESTful 방식에서 삭제 성공 시 일반적으로 반환 없이 상태 코드만 응답 (204 No Content 응답)을 사용
		return ResponseEntity.noContent().build(); //no Content:204 상태값
	}catch(Exception e) {
		//삭제할 ID가 없을 경우: 404 Not Found
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	}
}//end class
