# Base image with Java 17
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy all JAR files into the container
COPY /target/*.jar /app/

# Expose the necessary port
EXPOSE 8888

# Define environment variables for mail service and SMTP (Gmail)
ENV Mail_Service_PORT=8888
ENV SMTP_HOST=smtp.gmail.com
ENV SMTP_PORT=465
ENV SMTP_USERNAME=jeemishra2004@gmail.com
ENV SMTP_PASSWORD='tqld gtda vkee nnmn'
ENV SMTP_PROTOCOL=smtps

# Run all services using a shell script
CMD ["sh", "-c", "\
    java -jar *.jar --server.port=${Mail_Service_PORT} \
    --spring.mail.host=${SMTP_HOST} \
    --spring.mail.port=${SMTP_PORT} \
    --spring.mail.username=${SMTP_USERNAME} \
    --spring.mail.password=${SMTP_PASSWORD} \
    --spring.mail.protocol=${SMTP_PROTOCOL} & \
    wait"]
