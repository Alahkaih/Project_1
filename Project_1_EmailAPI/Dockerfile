FROM openjdk:8
COPY ./target/EmailAPI.jar/ /tmp
WORKDIR /tmp
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "EmailAPI.jar"]