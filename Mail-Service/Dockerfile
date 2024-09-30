# Base image with Java 17
FROM openjdk:17-jdk-slim
# Set the working directory inside the container
WORKDIR /app
# Copy all JAR files into the container
COPY /target/*.jar /app/
# Expose the necessary ports
EXPOSE 8888
# Define environment variables for each service
ENV Main-Service=8888
 

# Run all services using a shell script
CMD ["sh", "-c", "\
    java -jar *.jar --server.port=${Main_Service_PORT} & \
    wait"]
