plugins {
    id("java")
    id("application")
    id("com.github.johnrengelman.shadow") version "8.1.1"

}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.8.9")
    // JUnit 5
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:4.11.0")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("Main")
}


tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "Main"
    }
}

tasks {
    shadowJar {
        archiveBaseName.set("app")
        archiveClassifier.set("")
        archiveVersion.set(version.toString())
        manifest {
            attributes["Main-Class"] = "Main"
        }
    }
}

