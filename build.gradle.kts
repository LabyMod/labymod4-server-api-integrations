plugins {
    id("java")
    id("maven-publish")
}

group = "net.labymod.serverapi.integration"
version = System.getenv("VERSION") ?: "0.0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

val commonOutputDir = "${buildDir}/commonOutput"

tasks.register<Delete>("cleanCommonOutput") {
    delete(commonOutputDir)
}

tasks.named("clean") {
    dependsOn("cleanCommonOutput")
}

tasks.named("build") {
    dependsOn("cleanCommonOutput")
}

subprojects {
    plugins.apply("java-library")
    plugins.apply("maven-publish")

    group = rootProject.group
    version = rootProject.version

    repositories {
        mavenCentral()
        maven("https://dist.labymod.net/api/v1/maven/release/")
        mavenLocal()
    }

    dependencies {
        compileOnly("org.jetbrains:annotations:22.0.0")
        implementation("net.labymod.serverapi:core:0.0.0")
        compileOnly("com.google.auto.service:auto-service:1.1.1")
        annotationProcessor("com.google.auto.service:auto-service:1.1.1")
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        withSourcesJar()
    }

    fun adjustArchiveFileName(property: Property<String>) {
        var value = property.get()
        property.set("server-api-integration-$value")
    }

    tasks.jar {
        adjustArchiveFileName(archiveFileName)
    }

    //tasks.register("sourcesJar", Jar::class) {
    //    archiveClassifier.set("sources")
    //    adjustArchiveFileName(archiveFileName)
//
    //    from(sourceSets.main.get().allSource)
    //}

    var publishToken = System.getenv("PUBLISH_TOKEN")

    if (publishToken == null && project.hasProperty("net.labymod.distributor.publish-token")) {
        publishToken = project.property("net.labymod.distributor.publish-token").toString()
    }

    publishing {
        publications {
            create<MavenPublication>(project.name) {
                groupId = project.group.toString()
                artifactId = project.name
                version = project.version.toString()
                from(components["java"])
            }
        }

        repositories {
            maven(project.property("net.labymod.distributor-url").toString()) {
                name = "LabyMod-Distributor"

                authentication {
                    create<HttpHeaderAuthentication>("header")
                }

                credentials(HttpHeaderCredentials::class) {
                    name = "Publish-Token"
                    value = publishToken
                }
            }
        }
    }

    tasks.register<Copy>("copyToCommonOutput") {
        val commonOutputDir = project.rootProject.buildDir.resolve("commonOutput")

        // Copy regular JAR files
        from(tasks.named("jar").map { it.outputs.files })

        // Copy sources JAR files if they exist
        val sourcesJarTask = tasks.findByName("sourcesJar")
        if (sourcesJarTask != null) {
            from(sourcesJarTask.outputs.files)
        }

        into(commonOutputDir)
    }

    tasks.named("build") {
        dependsOn("copyToCommonOutput")
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}