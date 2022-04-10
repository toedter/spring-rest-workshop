FROM eclipse-temurin:17-alpine as builder
WORKDIR application
ARG JAR_FILE=backend/build/libs/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM eclipse-temurin:17-alpine
WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/application/ ./
COPY frontend/dist/frontend ./BOOT-INF/classes/static
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
