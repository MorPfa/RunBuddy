plugins {
    alias(libs.plugins.runbuddy.android.library)
}

android {
    namespace = "app.run.data"
}

dependencies {

    implementation(projects.core.domain)
    implementation(projects.core.connectivity.domain)
    implementation(projects.core.database)
    implementation(projects.run.domain)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.google.android.gms.play.services.location)
    implementation(libs.androidx.work)
    implementation(libs.koin.android.workmanager)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.espresso.core)
    implementation(libs.androidx.junit)
}