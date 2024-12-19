plugins {
    idea
}

group = rootProject.group
version = rootProject.version

idea {
    module {
        outputDir = file("build/classes/java/main")
        testOutputDir = file("build/classes/java/test")
    }
}
