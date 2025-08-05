plugins {
    id("java")
    id("com.diffplug.spotless") version "5.0.0"
    id("io.qameta.allure") version "2.12.0"
}

group = "ru.talykov"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    compileOnly ("org.projectlombok:lombok:1.18.38")
    annotationProcessor ("org.projectlombok:lombok:1.18.38")
    testImplementation("io.qameta.allure:allure-junit5:2.29.1")
    testImplementation("org.assertj:assertj-core:3.27.3")
}

tasks.test {
    useJUnitPlatform()
}