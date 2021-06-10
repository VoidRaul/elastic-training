FROM maven:3.6-jdk-11-slim AS builder
COPY . /build
WORKDIR /build
RUN mvn package --batch-mode -Dmaven.test.skip=true

FROM openjdk:11-jre-slim
EXPOSE 5000
VOLUME /tmp
COPY --from=builder /build/target/*.jar /app/app.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar"]
