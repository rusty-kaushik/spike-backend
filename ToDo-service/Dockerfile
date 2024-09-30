# Base image with Java 17
FROM openjdk:17-jdk-slim
# Set the working directory inside the container
WORKDIR /app
# Copy all JAR files into the container
COPY /target/*.jar /app/
# Expose the necessary ports
EXPOSE 9090
# Define environment variables for each service
ENV ToDo-Service=9090
 

# Run all services using a shell script
CMD ["sh", "-c", "\
    java -jar *.jar --server.port=${ToDo_Service_PORT} & \
    wait"]
