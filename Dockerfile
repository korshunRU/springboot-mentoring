FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=build/libs/solbeg.jar
WORKDIR /opt/app
COPY ${JAR_FILE} app.jar
COPY ./data-authors.csv .
COPY ./data-books.csv .
COPY ./data-tags.csv .
ENTRYPOINT ["java", "-jar", "app.jar"]