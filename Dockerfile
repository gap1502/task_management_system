FROM openjdk:17-alpine
EXPOSE 9000
ADD target/task_system-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]