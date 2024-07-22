# 소셜 곱셈 게임 (Social Multiplication Game)

이 프로젝트는 **Spring Cloud**를 활용하여 구현된 **소셜 곱셈 게임**입니다. 여러 개의 마이크로서비스를 통해 다양한 게임 기능을 제공하며, 각 서비스는 독립적으로 운영됩니다.

## 프로젝트 구성

프로젝트는 다음과 같은 마이크로서비스로 구성됩니다:

- **Gateway**: 클라이언트의 요청을 적절한 서비스로 라우팅하는 API Gateway입니다.
- **Service Discovery**: 서비스 등록 및 조회를 위한 Eureka 서버입니다.
- **Auth Service**: 사용자 등록 및 JWT 기반 인증 서비스를 제공합니다.
- **Multiplication Service**: 곱셈 문제를 생성하고 처리하는 서비스입니다.
- **Gamification Service**: 게임 점수, 배지 및 통계 기능을 처리합니다.

## 아키텍처

<p align="center">
    <img src="https://github.com/user-attachments/assets/b848fa40-1880-4953-9f43-66fd30ca96cc" alt="architecture"/>
</p>

## 기술 스택

- **Frontend**: Vue.js
- **Backend**: Spring Boot 3.3.1, Spring Cloud
- **보안**: Spring Security, JSON Web Token (JWT)
- **메시징**: RabbitMQ
- **데이터베이스**: JPA, MySQL
- **빌드 도구**: Gradle
- **컨테이너화**: Docker, Docker Compose
- **모니터링**: Prometheus, Grafana
