plugins {
    java
    id("net.ltgt.errorprone") version "2.0.2"
    checkstyle
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.mockito:mockito-core:4.0.0")

    errorprone("com.google.errorprone:error_prone_core:2.3.3")
    errorproneJavac("com.google.errorprone:javac:9+181-r4173-1")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

checkstyle {
    toolVersion = "9.0"
    configFile = rootProject.file("config/checkstyle/checkstyle.xml")
    configProperties["checkstyle.cache.file"] = "${buildDir}/checkstyle.cache"
    isIgnoreFailures = true
    isShowViolations = true
}