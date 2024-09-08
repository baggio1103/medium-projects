plugins {
    kotlin("jvm") version "1.9.22"
    application
}


group = "com.atomiccoding"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    applicationDefaultJvmArgs = listOf("-Dorg.slf4j.simpleLogger.showInitializationInfo=false")
}

dependencies {
    // https://mvnrepository.com/artifact/org.postgresql/postgresql
    implementation("org.postgresql:postgresql:42.7.4")
    implementation("org.flywaydb:flyway-core:10.17.3")
    implementation("com.zaxxer:HikariCP:5.1.0")
    runtimeOnly("org.flywaydb:flyway-database-postgresql:10.17.3")

    // Logging
    implementation("org.slf4j:slf4j-api:2.0.16")
    implementation("ch.qos.logback:logback-classic:1.5.8")


    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(19)
}