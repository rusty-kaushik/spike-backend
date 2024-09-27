# Base image with Java 17
FROM openjdk:17-jdk-slim
# Set the working directory inside the container
WORKDIR /app
# Copy all JAR files into the container
COPY /target/*.jar /app/
# Expose the necessary ports
EXPOSE 8383
# Define environment variables for each service
ENV COMMENT_SERVICE_PORT=8383
 

# Run all services using a shell script
CMD ["sh", "-c", "\
    java -jar comment-service-0.0.1-SNAPSHOT.jar --server.port=${COMMENT_SERVICE_PORT} & \
    wait"]
