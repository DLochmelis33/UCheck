FROM ubuntu:20.04

RUN apt update

RUN apt install -y git

# UTF-8 by default
#
RUN apt-get -qq update && \
    apt-get -qqy install gnupg2 locales && \
    locale-gen en_US.UTF-8 && \
    rm -rf /var/lib/apt/lists/*

ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8

#
# Pull Zulu OpenJDK binaries from official repository:
#
RUN apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys 0xB1998361219BD9C9 && \
    echo "deb http://repos.azulsystems.com/ubuntu stable main" >> /etc/apt/sources.list.d/zulu.list && \
    apt-get -qq update && \
    apt-get -qqy install zulu-11 && \
    rm -rf /var/lib/apt/lists/*

RUN git clone https://github.com/DLochmelis33/UCheck.git

WORKDIR "/UCheck"
RUN git fetch
RUN git checkout docker-setup
RUN chmod +x gradlew
RUN ./gradlew