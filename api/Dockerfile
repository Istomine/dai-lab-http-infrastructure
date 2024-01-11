FROM eclipse-temurin:11-alpine
COPY KirbyCreator/target/KirbyCreator-1.0-SNAPSHOT-jar-with-dependencies.jar /app.jar
# This is the port that your javalin application will listen on
EXPOSE 7000 
ENTRYPOINT ["java", "-jar", "/app.jar"]
