FROM openjdk:8-jre-alpine
WORKDIR /app
COPY target/yuoj-backedn-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8121
CMD ["java", "-jar", "app.jar"]
