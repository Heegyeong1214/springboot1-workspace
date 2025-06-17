-- mysql

use mywork;



CREATE TABLE todotbl(
id int  PRIMARY KEY AUTO_INCREMENT, /*My SQL*/
completed int(1) DEFAULT 0,
todoname  varchar(100) NOT NULL);

INSERT INTO todotbl(completed, todoname) VALUES( 0, '여행');
commit;
SELECT * FROM todotbl;

/*
[MySQL에서 BOOLEAN TYPE ]
MySql 에서 BOOLEAN TYPE 은 5버전대부터 사용이 가능하다.
실제로 BOOLEAN(BOOL) TYPE 으로 정의하면 TINYINT(1) 형식으로 정의 된다.
데이터 역시 TRUE는 1, FALSE 는 0 으로 저장이 된다.
물론 저장 할때는 TRUE, FALSE 로 저장이 가능하다.
하지만 불러올때는 1, 0 으로 나오므로 아래와 같이 sql문을 작성한다.

IF(칼럼명 , 'true', 'false') as 칼럼명

SELECT IF(completed , 'true', 'false') as completed  ;


-- oracle
CREATE TABLE todotbl(
id number  PRIMARY KEY,
completed number(1) DEFAULT 0,
todoname  VARCHAR2(100) NOT NULL);


CREATE SEQUENCE todotbl_id_seq
START WITH 1
INCREMENT By 1
NOCACHE
NOCYCLE;


INSERT INTO todotbl(id,completed, todoname) VALUES(todotbl_id_seq.nextval, 0, '여행');
commit;
SELECT * FROM todotbl;