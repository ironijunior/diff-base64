FROM maven:3.6.2-jdk-11
LABEL maintainer="ironimedina@gmail.com"
COPY . /usr/src/app
WORKDIR /usr/src/app
RUN mvn clean package --batch-mode
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "target/diff-base64.jar"]
