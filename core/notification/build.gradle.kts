plugins {
    alias(libs.plugins.runbuddy.android.library)
}

android {
    namespace = "app.core.notification"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.koin)
    implementation(projects.core.domain)
    implementation(projects.core.presentation.ui)
    implementation(projects.core.presentation.designsystem)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit.v115)
    androidTestImplementation(libs.androidx.espresso.core.v351)
}