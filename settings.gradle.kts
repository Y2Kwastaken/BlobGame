rootProject.name = "Blobs"
gradle.rootProject {
    group = "sh.miles.blobs"
    version = "1.0.0-SNAPSHOT"
}

include(
    "common",
    "graphics",
    "headless",
    "lwjgl3",
)
