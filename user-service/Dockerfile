# Base image with Java 17
FROM openjdk:17-jdk-slim
# Set the working directory inside the container
WORKDIR /app
# Copy all JAR files into the container
COPY /target/*.jar /app/
# Expose the necessary ports
EXPOSE 8181
# Define environment variables for each service
ENV USER_SERVICE_PORT=8181

# Make sure the upload directory exists inside the container
RUN mkdir -p /app/profile_pictures

# Run all services using a shell script
CMD ["sh", "-c", "\
    java -jar User_Service-0.0.1-SNAPSHOT.jar --server.port=${USER_SERVICE_PORT} & \
    wait"]
