plugins {
    java
    id("com.diffplug.spotless") version "7.0.0.BETA2"
}

repositories {
    mavenCentral()
}

java {
    // https://docs.gradle.org/current/userguide/toolchains.html
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

spotless {
    java {
        importOrder()
        removeUnusedImports()
    }
}

dependencies {
    implementation("io.swagger.parser.v3:swagger-parser:2.1.21")
    implementation("com.github.fge:json-schema-validator:2.2.6")
    testImplementation("org.hamcrest:java-hamcrest:2.0.0.0")
    testImplementation("io.cucumber:cucumber-java:7.18.1")
    testImplementation("io.cucumber:cucumber-junit:7.18.1")
    testImplementation("io.cucumber:cucumber-junit-platform-engine:7.18.1")
    testImplementation(platform("org.junit:junit-bom:5.11.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.platform:junit-platform-suite")
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
