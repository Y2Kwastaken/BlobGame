plugins {
    id("idea-setup")
    id("game-module")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

dependencies {
    implementation(project(":headless"))
}
