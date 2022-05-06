FROM openjdk:8
COPY ./target/ReimbursementAPI.jar/ /tmp
WORKDIR /tmp
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "ReimbursementAPI.jar"]