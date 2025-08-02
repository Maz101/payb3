import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    val iOsFrameworkBundleId = "org.yourname.paybe.sharedframework"

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = false
            freeCompilerArgs += "-Xbinary=bundleId=${iOsFrameworkBundleId}"

        }
    }
    
    jvm("desktop")
    
    sourceSets {
        val desktopMain by getting
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(libs.androidx.lifecycle.viewmodel)
                implementation(libs.androidx.lifecycle.runtimeCompose)
            }
            // Explicitly set the resource directories for commonMain
            // This will override any defaults or previously set paths like "src/commonMain/resources"
            resources.srcDirs("src/commonMain/composeResources")
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
        }
    }
}

project.afterEvaluate {
    val commonMainSourceSet = kotlin.sourceSets.getByName("commonMain")
    println("--- [After Evaluate] CommonMain Resource Directories ---")
    commonMainSourceSet.resources.srcDirs.forEach { dir ->
        println("Resource dir: ${dir.absolutePath}")
        // Let's also check if composeResources is where we expect
        val composeResourcesDir = project.file("src/commonMain/composeResources")
        println("  Expected composeResources path: ${composeResourcesDir.absolutePath}")
        println("  Expected composeResources exists: ${composeResourcesDir.exists()}")
        println("  Expected composeResources is directory: ${composeResourcesDir.isDirectory}")
        if (composeResourcesDir.exists() && composeResourcesDir.isDirectory) {
            val fontDir = project.file("src/commonMain/composeResources/font")
            println("    Expected font dir path: ${fontDir.absolutePath}")
            println("    Expected font dir exists: ${fontDir.exists()}")
            println("    Expected font dir is directory: ${fontDir.isDirectory}")
            if (fontDir.exists() && fontDir.isDirectory) {
                fontDir.listFiles()?.forEach { fileInFontDir ->
                    println("      Found in font dir: ${fileInFontDir.name}")
                } ?: println("      Font directory is empty or cannot be listed.")
            } else {
                println("    Font directory does not exist or is not a directory.")
            }
        } else {
            println("  composeResources directory does not exist or is not a directory.")
        }
    }
    println("--- [After Evaluate] End CommonMain Resource Directories ---")
}
// an

android {
    namespace = "com.example.paybe"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.example.paybe"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "com.example.paybe.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.example.paybe"
            packageVersion = "1.0.0"
        }
    }
}
