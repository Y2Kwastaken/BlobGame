project(":lwjgl3") {
    apply(plugin = "org.graalvm.buildtools.native")

    graalvmNative {
        binaries {
            main {
                imageName = appName
                mainClass = project.mainClassName
                requiredVersion = "23.0"
                buildArgs.add("-march=compatibility")
                jvmArgs.addAll("-Dfile.encoding=UTF8")
                sharedLibrary = false
                resources.autodetect()
            }
        }
    }

    tasks.named("run") {
        doLast {
            // Ensure that the state is not tracked for this task.
            doNotTrackState("Running the app should not be affected by Graal.")
        }
    }

    // Modified from https://lyze.dev/2021/04/29/libGDX-Internal-Assets-List/ ; thanks again, Lyze!
    // This creates a resource-config.json file based on the contents of the assets folder (and the libGDX icons).
    // This file is used by Graal Native to embed those specific files.
    // This has to run before nativeCompile, so it runs at the start of an unrelated resource-handling command.
    tasks.named("generateResourcesConfigFile") {
        doFirst {
            val assetsFolder = File("${project.rootDir}/assets/")
            val lwjgl3 = project(":lwjgl3")
            val resFolder = File("${lwjgl3.projectDir}/src/main/resources/META-INF/native-image/${lwjgl3.ext.get("appName")}")
            resFolder.mkdirs()
            val resFile = File(resFolder, "resource-config.json")
            resFile.delete()
            resFile.appendText(
                """{
  "resources":{
  "includes":[
    {
      "pattern": ".*("""
            )

            // This adds every filename in the assets/ folder to a pattern that adds those files as resources.
            fileTree(assetsFolder).forEach {
                resFile.appendText("""\\Q${it.name}\\E|""")
            }

            // We also match all of the window icon images this way and the font files that are part of libGDX.
            resFile.appendText(
                """libgdx.+\\.png|lsans.+)"
    }
  ]},
  "bundles":[]
}"""
            )
        }
    }
}
