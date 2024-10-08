plugins {
    alias(libs.plugins.runbuddy.android.library)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "app.core.connectivity.data"
}

dependencies {

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.play.services)
    implementation(libs.play.services.wearable)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.bundles.koin)

    implementation(projects.core.domain)
    implementation(projects.core.connectivity.domain)

    
    implementation(libs.androidx.espresso.core)
    implementation(libs.androidx.junit)

}