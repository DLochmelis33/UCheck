FROM openjdk:15-alpine

# gradle 
ADD ./gradlew ./build.gradle.kts ./settings.gradle.kts UCheck/
ADD ./gradle UCheck/gradle

# main sources
ADD ./src/main UCheck/src/main

WORKDIR "/UCheck"

# assemble distributions
RUN chmod +x gradlew && \
    ./gradlew assemble
