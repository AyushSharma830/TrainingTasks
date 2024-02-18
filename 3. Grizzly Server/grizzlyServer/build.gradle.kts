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
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.glassfish.jersey.containers:jersey-container-grizzly2-http:3.0.3")
    implementation("org.glassfish.jersey.inject:jersey-hk2:3.0.3")
    implementation("org.glassfish.grizzly:grizzly-http-server:4.0.2")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(11)
}