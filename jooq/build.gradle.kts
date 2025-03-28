import org.jooq.meta.jaxb.ForcedType
import org.jooq.meta.jaxb.Logging

val jooqPluginVersion = "3.19.11"
val postgresqlVersion = "42.7.4"

plugins {
    kotlin("jvm") version "2.1.10"
    id("nu.studer.jooq") version "9.0"
    id("org.flywaydb.flyway") version "11.4.0"
}

group = "com.atomiccoding"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Postgres, Flyway and Jooq
    implementation(libs.bundles.database)
    implementation(libs.jooq)
    jooqGenerator(libs.postgresSql)

    // Logging
    implementation(libs.bundles.logging)

    implementation(libs.bundles.coroutines)

    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

flyway {
    url = "jdbc:postgresql://localhost:5432/book-hub"
    user = "book-hub-user"
    password = "hashed-password"
    locations = arrayOf(
        "filesystem:src/main/resources/db/migration",
    )
}

jooq {
    version.set(jooqPluginVersion)
    edition.set(nu.studer.gradle.jooq.JooqEdition.OSS)
    configurations {
        create("main") {
            jooqConfiguration.apply {
                logging = Logging.INFO
                jdbc.apply {
                    driver = "org.postgresql.Driver"
                    url = flyway.url
                    user = flyway.user
                    password = flyway.password
                }
                generator.apply {
                    name = "org.jooq.codegen.DefaultGenerator"
                    database.apply {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        forcedTypes.addAll(listOf(
                            ForcedType().apply {
                                name = "varchar"
                                includeExpression = ".*"
                                includeTypes = "JSONB?"
                            },
                            ForcedType().apply {
                                name = "varchar"
                                includeExpression = ".*"
                                includeTypes = "INET"
                            }
                        ))
                    }
                    generate.apply {
                        isDeprecated = false
                        isRecords = false
                        isImmutablePojos = false
                        isFluentSetters = false
                    }
                    target.apply {
                        packageName = "com.atomicCoding.generated"
                        directory = "build/generated-src/jooq"
                    }
                    strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
                }
            }
        }
    }
}

val jooqTask = tasks.named("generateJooq").get()

val flywayTask = tasks.find { it.name == "flywayMigrate" }

jooqTask.dependsOn(flywayTask)

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(19)
}