plugins {
    alias(libs.plugins.runbuddy.android.library)
}

android {
    namespace = "app.run.network"
}

dependencies {

    implementation(projects.core.domain)
    implementation(projects.core.data)

    implementation(libs.androidx.espresso.core)
    implementation(libs.androidx.junit)
}