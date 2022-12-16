FROM adoptopenjdk:11-jre-hotspot
ADD build/libs/kikunditestrepo-1.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]