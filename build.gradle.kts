// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false // kotiln = 1.9.0
    id("com.google.dagger.hilt.android") version "2.52" apply false
    id("com.google.gms.google-services") version "4.3.8" apply false
}