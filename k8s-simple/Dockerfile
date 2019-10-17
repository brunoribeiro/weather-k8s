FROM openjdk:8-jdk

RUN apt-get update && \
  apt-get install -y maven

WORKDIR /app
COPY . /app
RUN mvn clean install

FROM openjdk:8-alpine
EXPOSE 8080
WORKDIR /app
COPY --from=0 /app/target/k8s-simple-0.0.1-SNAPSHOT.jar /k8s-simple-0.0.1-SNAPSHOT.jar
CMD java -jar /k8s-simple-0.0.1-SNAPSHOT.jar
