plugins {
    alias(libs.plugins.runbuddy.android.library)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "app.run.location"

}

dependencies {

    implementation(projects.core.domain)
    implementation(projects.run.domain)

    implementation(libs.androidx.core.ktx)

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.material3)
    debugImplementation(libs.androidx.compose.ui.tooling)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.google.android.gms.play.services.location)

    implementation(libs.androidx.espresso.core)
    implementation(libs.androidx.junit)
}