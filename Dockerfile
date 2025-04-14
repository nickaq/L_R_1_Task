FROM eclipse-temurin:23-jdk AS build

RUN apt-get update && \
    apt-get install -y curl tar && \
    curl -fsSL https://archive.apache.org/dist/maven/maven-3/3.9.2/binaries/apache-maven-3.9.2-bin.tar.gz \
    | tar -xzC /opt && \
    ln -s /opt/apache-maven-3.9.2/bin/mvn /usr/bin/mvn
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:23-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
