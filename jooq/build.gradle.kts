import org.jooq.meta.jaxb.ForcedType
import org.jooq.meta.jaxb.Logging

plugins {
    kotlin("jvm") version "1.9.22"
    id("nu.studer.jooq") version "10.1"
    id("org.flywaydb.flyway") version "11.9.1"
}

group = "com.atomiccoding"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.flyway.postgres)
    implementation(libs.bundles.database)

    implementation(libs.bundles.logging)

    // jooq be omitted, can be configured by the plugin
    implementation(libs.jooq)
    jooqGenerator(libs.postgresql)

    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

flyway {
    driver = "org.postgresql.Driver"
    url = "jdbc:postgresql://localhost:5432/ecommerce"
    user = "ecommerce-user"
    password = "ecommerce-password"
    locations = arrayOf(
        "filesystem:src/main/resources/db/migration",
    )
}

jooq {
    version.set(libs.versions.jooq.get())
    edition.set(nu.studer.gradle.jooq.JooqEdition.OSS)
    configurations {
        create("main") {
            jooqConfiguration.apply {
                logging = Logging.INFO

                jdbc.apply {
                    driver = flyway.driver
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
println("Task: ${flywayTask?.name}")

jooqTask.dependsOn(flywayTask)

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}