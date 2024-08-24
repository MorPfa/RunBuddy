plugins {
    alias(libs.plugins.runbuddy.android.library)
}

android {
    namespace = "app.runbuddy.analytics.data"
}

dependencies {

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    implementation(libs.kotlinx.coroutines.core)
    implementation(projects.core.database)
    implementation(projects.core.domain)
    implementation(projects.analytics.domain)

}