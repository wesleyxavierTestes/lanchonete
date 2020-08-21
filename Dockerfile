
FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS
ADD target/lanchonete-0.0.1-SNAPSHOT.jar lanchonete.jar
EXPOSE 8080
ENTRYPOINT exec java $JAVA_OPTS -jar lanchonete.jar
# For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
#ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar lanchonete.jar
