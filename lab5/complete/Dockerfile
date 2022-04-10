FROM eclipse-temurin:17-alpine as builder
WORKDIR application
ARG JAR_FILE=backend/build/libs/backend.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM eclipse-temurin:17-alpine
WORKDIR application
COPY --from=builder application/dependencies/ ./
RUN true
COPY --from=builder application/snapshot-dependencies/ ./
RUN true
COPY --from=builder application/spring-boot-loader/ ./
RUN true
COPY --from=builder application/application/ ./
RUN true
COPY frontend/dist/frontend ./BOOT-INF/classes/static
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
