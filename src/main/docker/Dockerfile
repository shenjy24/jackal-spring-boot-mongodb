FROM java:8
VOLUME /tmp
ADD gameweb.jar /app.jar
ADD conf /conf
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar","--spring.config.location=/conf/application.yml"]