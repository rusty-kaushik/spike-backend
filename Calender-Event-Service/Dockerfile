# Base image with Java 17
FROM openjdk:17-jdk-slim
# Set the working directory inside the container
WORKDIR /app
# Copy all JAR files into the container
COPY /target/*.jar /app/
# Expose the necessary ports
EXPOSE 8989
# Define environment variables for each service
ENV Calender-Event-Service=8989
 

# Run all services using a shell script
CMD ["sh", "-c", "\
    java -jar *.jar --server.port=${Calender_Event_Service_PORT} & \
    wait"]
