FROM maven:3.8.5-openjdk-17 AS build
COPY src /home/app/src
COPY pom.xml /home/app
VOLUME "~/volumes/.m2"
RUN --mount=type=cache,target=/root/.m2 mvn -f /home/app/pom.xml -DskipTests=true clean package

FROM openjdk:17
EXPOSE 8080
COPY --from=build /home/app/target/orion-0.0.1-SNAPSHOT.jar /home/app/
WORKDIR /home/app
RUN sh -c 'touch orion-0.0.1-SNAPSHOT.jar'
ENTRYPOINT java -jar orion-0.0.1-SNAPSHOT.jar