import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id ("com.github.johnrengelman.shadow")

}

dependencies {
    implementation ("com.google.guava:guava")
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("hw01-gradle")
        archiveClassifier.set("")
        manifest {
                attributes(mapOf("Main-Class" to "ru.otus.App"))
        }
    }
    build {
        dependsOn(shadowJar)
    }
}
