# Spring in Action 6장
- https://github.com/Jpub/SpringInAction5 
- 제공된 교제 예제 코드 참고 하여 수정함

## 구조  
- `tacocloud-api` : The REST API
- `tacocloud-data` : The persistence module
- `tacocloud-domain` : The domain types
- `tacocloud-security` : The security module (TODO: Not fully functional yet.)
- `tacos` : The main module that pulls the other modules together and provides the Spring Boot main class.


## Frontend
- Angular 코드는 기존의 실습 예제 그대로 이용하는 대신 폴더를 분리하여 실행
https://github.com/cusbert/spring-in-action-5th/tree/main/tacocloud-ui
tacocloud-ui 폴더로 이동
```bash
npm install
npm start
```

## Backend
https://github.com/cusbert/spring-in-action-5th/tree/main/ch6
- swagger 로 확인
  http://localhost:8080/swagger-ui.html