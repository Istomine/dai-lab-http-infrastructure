FROM eclipse-temurin:11-alpine
COPY KirbyCreator/target/KirbyCreator-1.0-SNAPSHOT.jar /app.jar
# This is the port that your javalin application will listen on
EXPOSE 3001
ENTRYPOINT ["java", "-jar", "/app.jar"]
