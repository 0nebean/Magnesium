FROM openjdk:8u191-jre-alpine

MAINTAINER 0neBean

ADD ./app /app

RUN mv ./app/cacerts /usr/lib/jvm/java-1.8-openjdk/jre/lib/security

ENTRYPOINT /app/server/bin/startup.sh

