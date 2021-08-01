# Spring In Action 9장

## 환경 구성
### rabbitMQ docker 로 설치
```bash
docker run -d --hostname my-rabbit --name some-rabbit -p 5672:5672 -p 8090:15672 -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=secret rabbitmq:3-management
```


## 구조
- `api` : The REST API
- `data` : The persistence
- `domain` : The domain types
- `security` : The security module
- `restclient` : Client code that consumes the API exposed from `tacocloud-api`.
- `messaging` > `jms` : Messaging module that sends messages using JMS
- `messaging` > `rabbitMQ` : Messaging module that sends messages using RabbitMQ
- `messaging` > `kafka` : Messaging module that sends messages using Kafka
  

- `taco-cloud-consumer` : Messaging module that receive messages using JMS,RabbitMQ,Kafka


## Frontend
- Angular 코드는 기존의 실습 예제 그대로 이용하는 대신 폴더를 분리하여 실행
  https://github.com/cusbert/spring-in-action-5th/tree/main/tacocloud-ui
  tacocloud-ui 폴더로 이동
```bash
npm install
npm start
```

## Backend - producer
https://github.com/cusbert/spring-in-action-5th/tree/main/ch8/taco-cloud
- swagger 로 확인
  http://localhost:8080/swagger-ui.html

## Backend - consumer
https://github.com/cusbert/spring-in-action-5th/tree/main/ch8/taco-cloud-consumer
- swagger 로 확인
  http://localhost:8090/swagger-ui.html