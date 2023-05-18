plugins {
    java
}

group = "com.github.signed.swagger"
version = "0.2.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.swagger:swagger-parser:1.0.23")
    implementation("com.github.fge:json-schema-validator:2.2.6")
    testImplementation("org.hamcrest:java-hamcrest:2.0.0.0")
    testImplementation("io.cucumber:cucumber-java:7.12.0")
    testImplementation("io.cucumber:cucumber-junit:7.12.0")
    testImplementation("io.cucumber:cucumber-junit-platform-engine:7.12.0")
    testImplementation(platform("org.junit:junit-bom:5.9.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(8)
    options.encoding = "UTF-8"
    options.compilerArgs.addAll(listOf("-Xlint", "-Xlint:-serial", "-Xlint:-path"))
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
    systemProperty("cucumber.junit-platform.naming-strategy", "long")
}

tasks.named<Wrapper>("wrapper") {
    distributionType = Wrapper.DistributionType.ALL
}