plugins {
    id("idea-setup")
}

//allprojects {
//    apply(plugin = "eclipse")
//    apply(plugin = "idea")
//
//    idea {
//        module {
//            outputDir = file("build/classes/java/main")
//            testOutputDir = file("build/classes/java/test")
//        }
//    }
//}
//
//configure(subprojects) {
//    apply(plugin = "java-library")
//    val sourceCompatibility = 21
//
//    tasks.register("generateAssetList") {
//        inputs.dir("${project.rootDir}/assets/")
//        // projectFolder/assets
//        val assetsFolder = File("${project.rootDir}/assets/")
//        // projectFolder/assets/assets.txt
//        val assetsFile = File(assetsFolder, "assets.txt")
//        // delete that file in case we've already created it
//        assetsFile.delete()
//
//        // iterate through all files inside that folder
//        // convert it to a relative path
//        // and append it to the file assets.txt
//        fileTree(assetsFolder).map { assetsFolder.relativeTo(it) }.sorted().forEach {
//            assetsFile.appendText("$it\n")
//        }
//    }
//
//    tasks.getByName("processResources").dependsOn("generateAssetList")
//
//    tasks.getByName<JavaCompile>("compileJava") {
//        options.isIncremental = true
//    }
//}
//
//subprojects {
//    version = rootProject.version
//
//    repositories {
//        mavenCentral()
//        maven("https://s01.oss.sonatype.org")
//        // You may want to remove the following line if you have errors downloading dependencies.
//        mavenLocal()
//        maven("https://oss.sonatype.org/content/repositories/snapshots/")
//        maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
//        maven("https://jitpack.io")
//    }
//}
//
//eclipse.project.name = "AlgorithmIdle-parent"
