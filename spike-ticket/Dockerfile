FROM openjdk:17-jdk-slim
# Set the working directory inside the container
WORKDIR /app
# Copy all JAR files into the container
COPY /target/*.jar /app/
# Expose the necessary ports
EXPOSE 8686
# Define environment variables for each service
ENV spike-ticket_PORT=8686

# Create the directory for the pictures inside the container
RUN mkdir -p /app/pictures

# Run all services using a shell script
CMD ["sh", "-c", "\
    java -jar *.jar --server.port=${spike-ticket_PORT} & \
    wait"]
