FROM eclipse-temurin:17 AS build
WORKDIR /workspace/app

COPY mvnw pom.xml ./
COPY .mvn .mvn
RUN ./mvnw dependency:go-offline -B

COPY src src
RUN ./mvnw package -DskipTests

FROM eclipse-temurin:17-jre
VOLUME /tmp
COPY --from=build /workspace/app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]