# 1st stage, build the app 
FROM maven:3.9.9-eclipse-temurin-21-alpine AS build

ENV DB_HOST=exercise_db
ENV DB_PORT=9092

WORKDIR /helidon

COPY . .

RUN chmod +x /helidon/src/main/resources/keys/generate-keys.sh && /helidon/src/main/resources/keys/generate-keys.sh
RUN mvn clean install -DskipTests

# 2nd stage, build the runtime image 
FROM eclipse-temurin:21-jre
WORKDIR /helidon

# Copy the binary built in the 1st stage
COPY --from=build /helidon/target ./

ENTRYPOINT ["java", "-Dmp.config.profile=docker", "-jar", "api.jar"]