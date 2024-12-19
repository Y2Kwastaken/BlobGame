plugins {
    id("idea-setup")
    id("game-module")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

dependencies {
    api(libs.libgdx.api)
    compileOnly(project(":headless"))

    if (properties["enableGraalNative"] == "true") implementation(libs.graal.helper.annotations)
}
