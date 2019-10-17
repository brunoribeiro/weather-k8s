FROM openjdk:8-jdk

RUN apt-get update && \
  apt-get install -y maven

WORKDIR /app
COPY . /app
RUN mvn clean install

FROM openjdk:8-alpine
EXPOSE 8080
WORKDIR /app
COPY --from=0 /app/target/weather-bff-0.0.1-SNAPSHOT.jar /weather-bff-0.0.1-SNAPSHOT.jar
CMD java -jar /weather-bff-0.0.1-SNAPSHOT.jar
