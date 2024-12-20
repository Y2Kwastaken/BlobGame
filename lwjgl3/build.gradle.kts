import io.github.fourlastor.construo.Target
import org.gradle.jvm.tasks.Jar

plugins {
    application
    id("game-module")
    id("idea-setup")
    alias(libs.plugins.construo)
    alias(libs.plugins.graalvm) apply (false)
}

if (properties["enableGraalNative"] == "true") apply(libs.plugins.graalvm)

sourceSets.main {
    resources.srcDir(rootProject.file("assets"))
}
val mainClassName = "sh.miles.algidle.lwjgl3.Lwjgl3Launcher"
application.mainClass.set(mainClassName)

dependencies {
    implementation(libs.libgdx.lwjgl3)
    implementation(variantOf(libs.libgdx.platform) { classifier("natives-desktop") })
    implementation(libs.guava)
    implementation(project(":user_interface"))
    implementation(project(":headless"))

    if (properties["enableGraalNative"] == "true") implementation(libs.graal.helper.lwjgl3)
}

tasks.named<JavaExec>("run") {
    workingDir = rootProject.file("assets")
    isIgnoreExitValue = true

    if (System.getProperty("os.name").lowercase().contains("mac")) {
        jvmArgs = listOf("-XstartOnFirstThread")
    }
}

tasks.jar {
    // Sets the name of the .jar file this produces to the name of the game or app, with the version after.
    archiveFileName.set("AlgorithmIdle-${project.version}.jar")

    // The duplicatesStrategy matters starting in Gradle 7.0; this setting works.
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    // Make sure runtimeClasspath is included
    dependsOn(configurations.runtimeClasspath)

    // Include runtimeClasspath files, extracting them if they are not directories
    from(configurations.runtimeClasspath.get().map {
        if (it.isDirectory) it else zipTree(it)
    })

    // Exclude unnecessary duplicate files in the output JAR
    exclude("META-INF/INDEX.LIST", "META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA")

    // Exclude some files from dependencies
    dependencies {
        exclude("META-INF/INDEX.LIST", "META-INF/maven/**", "windows/x86/**")
    }

    // Set the manifest to make the JAR runnable
    manifest {
        attributes["Main-Class"] = application.mainClass.get()
    }

    // Set the file as executable, which might be required on some systems to make the JAR runnable
    doLast {
        file(archiveFile).setExecutable(true, false)
    }
}

construo {
    name.set(project.name)
    humanName.set(project.name)
    version.set(project.version.toString())

    targets {
        create("linuxX64", Target.Linux::class) {
            architecture.set(Target.Architecture.X86_64)
            jdkUrl.set("https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.12%2B7/OpenJDK17U-jdk_x64_linux_hotspot_17.0.12_7.tar.gz")
        }

        create("macM1", Target.MacOs::class) {
            architecture.set(io.github.fourlastor.construo.Target.Architecture.AARCH64)
            jdkUrl.set("https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.12%2B7/OpenJDK17U-jdk_aarch64_mac_hotspot_17.0.12_7.tar.gz")
            identifier.set("sh.miles.algidle.AlgorithmIdle")
            macIcon.set(project.file("icons/logo.icns"))
        }

        create("macX64", Target.MacOs::class) {
            architecture.set(Target.Architecture.X86_64)
            jdkUrl.set("https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.12%2B7/OpenJDK17U-jdk_x64_mac_hotspot_17.0.12_7.tar.gz")
            identifier.set("sh.miles.algidle.AlgorithmIdle")
            macIcon.set(project.file("icons/logo.icns"))
        }

        create("winX64", Target.Windows::class) {
            architecture.set(Target.Architecture.X86_64)
            jdkUrl.set("https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.12%2B7/OpenJDK17U-jdk_x64_windows_hotspot_17.0.12_7.zip")
        }
    }
}

tasks.register("dist") {
    dependsOn(tasks.named("jar"))
}

distributions {
    named("main") {
        contents {
            into("libs") {
                // Get all runtime classpath files
                val runtimeClasspathFiles = project.configurations.runtimeClasspath.get().files

                // Get the generated JAR file's name
                val jarFileName = (project.tasks.named("jar").get() as Jar).archiveFileName.get()
                // Filter and exclude the jar file from the runtimeClasspath
                runtimeClasspathFiles.filter { file ->
                    // Exclude the JAR file produced by the "jar" task
                    file.name != jarFileName
                }.forEach { file ->
                    exclude(file.name)
                }
            }
        }
    }
}

tasks.named<CreateStartScripts>("startScripts") {
    dependsOn(":lwjgl3:jar")
    classpath = project.tasks.named("jar").get().outputs.files
}

if (project.hasProperty("enableGraalNative") && project.property("enableGraalNative") == "true") {
    apply(from = "nativeimage.gradle.kts")
}
