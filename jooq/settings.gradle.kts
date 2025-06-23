buildscript {
    dependencies {
        classpath("org.flywaydb:flyway-database-postgresql:11.9.1")
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "jooq"

