plugins {
    java
    id("net.ltgt.errorprone") version "2.0.2"
    checkstyle
    jacoco
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.mockito:mockito-core:4.0.0")
    implementation("info.picocli", "picocli", "4.6.1")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.0.1")

    errorprone("com.google.errorprone:error_prone_core:2.9.0")
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
    isShowViolations = true
    isIgnoreFailures = false
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}

tasks.jacocoTestReport {

    dependsOn(tasks.test) // tests are required to run before generating the report
    reports {
        html.isEnabled = true
        xml.isEnabled = true
        csv.isEnabled = false

        html.destination = file("$buildDir/reports/jacoco/test/html")
        xml.destination = file("$buildDir/reports/jacoco/test/jacocoTestReport.xml")
    }

    finalizedBy("jacocoTestCoverageVerification")

}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = "0.65".toBigDecimal()
            }
        }
    }
}

