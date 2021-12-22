FROM openjdk:15-alpine

# gradle 
ADD ./gradlew ./build.gradle.kts ./settings.gradle.kts UCheck/
ADD ./gradle UCheck/gradle

# main and test sources
ADD ./src UCheck/src

# checkstyle config
ADD ./config UCheck/config

WORKDIR "/UCheck"

# gradle 'build' task runs all tests and linters
RUN chmod +x gradlew && \
    ./gradlew build 
