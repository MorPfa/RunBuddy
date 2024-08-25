plugins {
    alias(libs.plugins.runbuddy.android.library.compose)
}

android {
    namespace = "app.core.presentation.designsystem_wear"

    defaultConfig {
        minSdk = 30
    }
}

dependencies {
    api(projects.core.presentation.designsystem)
    implementation(libs.androidx.wear.compose.material)
}