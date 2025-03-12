FROM openjdk:24
WORKDIR /root/
ADD build/libs/bluewhale-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
EXPOSE 443
EXPOSE 8443

CMD ["java", \
     "-XX:+UnlockExperimentalVMOptions", \
     "--enable-preview", \
     "-Dspring.profiles.active=default,docker", \
     "-jar", \
     "app.jar"]