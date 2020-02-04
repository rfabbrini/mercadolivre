FROM alpine-jdk:base
MAINTAINER javaonfly
COPY target/prova-1.0.jar /opt/lib/
ENTRYPOINT ["/usr/bin/java"]
CMD ["-jar", "/opt/lib/prova-1.0.jar"]
EXPOSE 8080
