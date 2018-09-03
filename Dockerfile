FROM openjdk:8
ADD nimbusjosesignverifyexample.jar nimbusjosesignverifyexample.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","nimbusjosesignverifyexample.jar"]