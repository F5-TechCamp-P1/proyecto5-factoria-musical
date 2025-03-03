FROM eclipse-temurin:21-jdk-alpine

RUN mkdir -p /opt/factoria_musical

WORKDIR /opt/factoria_musical

COPY mvnw mvnw.cmd ./

COPY .mvn .mvn/

COPY pom.xml ./

RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests

EXPOSE 8000

COPY /target/factoria-musical-1.0-SNAPSHOT.jar /opt/factoria_musical/

RUN chmod +x /opt/factoria_musical/factoria-musical-1.0-SNAPSHOT.jar

CMD ["java", "-jar", "/opt/factoria_musical/factoria-musical-1.0-SNAPSHOT.jar"]
