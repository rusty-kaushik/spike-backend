# Base image with Java 17
FROM openjdk:17-jdk-slim
# Set the working directory inside the container
WORKDIR /app
# Copy all JAR files into the container
COPY /target/*.jar /app/
# Expose the necessary ports
EXPOSE 8089
# Define environment variables for each service
ENV BFF_SERVICE_PORT=8089
 

# Run all services using a shell script
CMD ["sh", "-c", "\
    java -jar SecureGate-0.0.1-SNAPSHOT.jar --server.port=${BFF_SERVICE_PORT} & \
    wait"]
