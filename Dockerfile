FROM maven:3.6.3-openjdk-17

RUN mkdir job4j_dreamjob

WORKDIR job4j_dreamjob

COPY . .

RUN mvn package -Dmaven.test.skip=true

CMD ["mvn", "liquibase:update", "-Pdocker"]

CMD ["java", "-jar", "target/job4j_dreamjob-1.0-SNAPSHOT.jar"]