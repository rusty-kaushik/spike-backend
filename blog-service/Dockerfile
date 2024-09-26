# Base image with Java 17
FROM openjdk:17-jdk-slim
 
# Set the working directory inside the container
WORKDIR /app
 
# Copy all JAR files into the container
COPY /target/*.jar /app/

RUN mkdir -p /app/profile_pictures


COPY opt/java-app/user/profile_pictures /app/profile_pictures
 
 
# Expose the necessary ports
EXPOSE 8282 
 
# Define environment variables for each service
ENV BLOG_SERVICE_PORT=8282

 
# Run all services using a shell script
CMD ["sh", "-c", "\
    java -jar blog-service-0.0.1-SNAPSHOT.jar --server.port=${BLOG_SERVICE_PORT} & \
    wait"]
