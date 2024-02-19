plugins {
    kotlin("jvm") version "1.9.22"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation("org.glassfish.jersey.containers:jersey-container-grizzly2-http:2.19")
    implementation("org.glassfish.jersey.containers:jersey-container-servlet-core:2.19")
    implementation("org.eclipse.jetty:jetty-servlet:9.2.12.v20150709")
    implementation("org.glassfish.jersey.media:jersey-media-multipart:2.19")
    implementation("org.glassfish.jersey.media:jersey-media-json-jackson:2.19")
    implementation("com.fasterxml.jackson.core:jackson-core:2.5.3")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.5.3")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.5.3")
    implementation("javax.xml.bind:jaxb-api:2.3.1")

    implementation("com.google.code.gson:gson:2.8.9")
    api ("org.json:json:20170516")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(11)
}