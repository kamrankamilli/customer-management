FROM eclipse-temurin:17-jdk-jammy
LABEL mentainer="kamilli818@gmail.com"
WORKDIR /app
COPY target/customer-management-0.0.1-SNAPSHOT.jar /app/customer-management.jar
ENTRYPOINT ["java", "-jar", "customer-management.jar"]