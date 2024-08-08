plugins {
    alias(libs.plugins.runbuddy.android.library)
    alias(libs.plugins.runbuddy.jvm.ktor)
}

android {
    namespace = "app.core.data"
}

dependencies {

    implementation(libs.bundles.koin)
    implementation(projects.core.domain)
    implementation(projects.core.database)

    implementation(libs.timber)
    implementation(libs.androidx.espresso.core)
    implementation(libs.androidx.junit)
}