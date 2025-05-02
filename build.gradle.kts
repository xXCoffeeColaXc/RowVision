plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android)    apply false
    alias(libs.plugins.kotlin.compose)    apply false
    // Hilt Gradle plugin (literal ID, version from libs.versions)
    id("com.google.dagger.hilt.android") version "2.56.1" apply false
}