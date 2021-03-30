# Not in use
FROM openjdk:latest
WORKDIR /root
COPY target/shorturl-0.1.0.jar /root
EXPOSE 8090/tcp
ENTRYPOINT java -jar shorturl-0.1.0.jar
