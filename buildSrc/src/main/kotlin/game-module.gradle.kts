plugins {
    `java-library`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven("https://s01.oss.sonatype.org")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    maven("https://jitpack.io")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21

    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.register("generateAssetList") {
    inputs.dir("${project.rootDir}/assets/")
    // projectFolder/assets
    val assetsFolder = File("${project.rootDir}/assets/")
    // projectFolder/assets/assets.txt
    val assetsFile = File(assetsFolder, "assets.txt")
    // delete that file in case we've already created it
    assetsFile.delete()

    // iterate through all files inside that folder
    // convert it to a relative path
    // and append it to the file assets.txt
    fileTree(assetsFolder).map { assetsFolder.relativeTo(it) }.sorted().forEach {
        assetsFile.appendText("$it\n")
    }
}

tasks.processResources {
    dependsOn("generateAssetList")
}

tasks.getByName<JavaCompile>("compileJava") {
    options.isIncremental = true
}
