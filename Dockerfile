FROM openjdk:11-oracle
LABEL maintainer="ironimedina@gmail.com"
COPY target/diff-base64.jar diff-base64.jar
EXPOSE 8080
CMD ["java", "-jar", "./diff-base64.jar"]