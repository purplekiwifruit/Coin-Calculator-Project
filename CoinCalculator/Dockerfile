FROM maven:3.8.5-openjdk-17
WORKDIR /app

# Copy the pom.xml and fetch dependencies
COPY ./pom.xml ./pom.xml
RUN mvn dependency:go-offline

# Copy the source code and configuration file
COPY ./src ./src
COPY ./config.yml ./config.yml

# Build the application
RUN mvn clean package

# Move the built jar to the target location
RUN mv ./target/CoinCalculator-1.0-SNAPSHOT.jar /app/backend.jar

# Expose the port the backend listens on
EXPOSE 8080

# Set command to run the application
CMD ["java", "-jar", "/app/backend.jar", "server", "/app/config.yml"]
