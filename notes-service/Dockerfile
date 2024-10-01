FROM openjdk:17-jdk-slim
# Set the working directory inside the container
WORKDIR /app
# Copy all JAR files into the container
COPY /target/*.jar /app/
# Expose the necessary ports
EXPOSE 8585
# Define environment variables for each service
ENV notes-service=8585

# Run all services using a shell script
CMD ["sh", "-c", "\
    java -jar *.jar --server.port=${notes_service_PORT} & \
    wait"]
