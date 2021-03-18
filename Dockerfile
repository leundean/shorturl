FROM openjdk:12-jdk-alpine
WORKDIR /root
COPY target/shorturl-0.1.0.jar /root
EXPOSE 8090/tcp
ENTRYPOINT java -jar shorturl-0.1.0.jar
