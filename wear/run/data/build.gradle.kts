plugins {
    alias(libs.plugins.runbuddy.android.library)
}

android {
    namespace = "runbuddy.wear.run.data"

    defaultConfig {
        minSdk = 30
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.health.services.client)
    implementation(libs.bundles.koin)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit.v115)
    androidTestImplementation(libs.androidx.espresso.core.v351)
}