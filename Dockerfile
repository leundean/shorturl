# Not in use
FROM openjdk:latest
ENV MY_NAME="JohnDoe"
WORKDIR /root
COPY target/shorturl-0.1.0.jar /root
EXPOSE 8090/tcp
ENTRYPOINT java -jar shorturl-0.1.0.jar
