rootProject.name = "AlgorithmIdle"
gradle.rootProject {
    group = "sh.miles.algidle"
    version = "1.0.0-SNAPSHOT"
}

include(
    "headless",
    "user_interface",
    "lwjgl3",
)
