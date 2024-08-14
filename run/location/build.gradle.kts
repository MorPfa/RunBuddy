plugins {
    alias(libs.plugins.runbuddy.android.library)

}

android {
    namespace = "app.run.location"

}

dependencies {


    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.koin)
    
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.google.android.gms.play.services.location)

    implementation(libs.androidx.espresso.core)
    implementation(libs.androidx.junit)

    implementation(projects.core.domain)
    implementation(projects.run.domain)

}