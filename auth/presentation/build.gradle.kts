plugins {
    alias(libs.plugins.runbuddy.android.feature.ui)
}

android {
    namespace = "app.auth.presentation"
}

dependencies {
    implementation(projects.auth.domain)
    implementation(projects.core.domain)
}