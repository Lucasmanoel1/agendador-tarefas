FROM gradle:8-jdk17 as build
WORKDIR /app
COPY . .
RUN gradle build --no-daemon

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=build /app/build/libs/*.jar /app/agendador-tarefas.jar

EXPOSE 8081

CMD ["java", "-jar", "/app/agendador-tarefas.jar"]