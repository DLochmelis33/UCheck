FROM ucheck:latest

# build project
RUN ./gradlew build

# VOLUME /out
# RUN cp build/distributions/*.zip /out

# build command: docker build --rm -f "dockerfiles\build.Dockerfile" -t ucheck_built:latest "dockerfiles"