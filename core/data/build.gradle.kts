plugins {
    alias(libs.plugins.runbuddy.android.library)
}

android {
    namespace = "app.core.data"
}

dependencies {

    implementation(projects.core.domain)
    implementation(projects.core.database)

    implementation(libs.timber)
    implementation(libs.androidx.espresso.core)
    implementation(libs.androidx.junit)
}