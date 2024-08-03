plugins {
    alias(libs.plugins.runbuddy.android.library.compose)

}

android {
    namespace = "app.core.presentation.ui"
}

dependencies {

    implementation(projects.core.domain)
    implementation(projects.core.presentation.designsystem)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.espresso.core)
    implementation(libs.androidx.junit)
}