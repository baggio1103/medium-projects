import org.jooq.meta.jaxb.ForcedType
import org.jooq.meta.jaxb.Logging

val jooqPluginVersion = "3.19.11"
val postgresqlVersion = "42.7.4"

plugins {
    kotlin("jvm") version "1.9.22"
    id("nu.studer.jooq") version "9.0"
    id("org.flywaydb.flyway") version "9.22.3"
}

group = "com.atomiccoding"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.postgresql:postgresql:$postgresqlVersion")
    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation("org.flywaydb:flyway-database-postgresql:10.17.3")

    // Logging
    implementation("org.slf4j:slf4j-api:2.0.16")
    implementation("ch.qos.logback:logback-classic:1.5.8")

    // JOOQ
    implementation("org.jooq:jooq:$jooqPluginVersion")
    jooqGenerator("org.postgresql:postgresql:$postgresqlVersion")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

flyway {
    url = "jdbc:postgresql://localhost:5432/posts"
    user = "posts"
    password = "posts-password"
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