# gradle:7.3.1-jdk17 이미지를 기반으로 함
FROM krmp-d2hub-idock.9rum.cc/goorm/gradle:7.3.1-jdk17

ADD ./build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]