plugins {
    alias(libs.plugins.runbuddy.android.library)
    alias(libs.plugins.runbuddy.jvm.ktor)

}

android {
    namespace = "app.run.network"
}

dependencies {


    implementation(libs.bundles.koin)
    implementation(projects.core.domain)
    implementation(projects.core.data)

    implementation(libs.androidx.espresso.core)
    implementation(libs.androidx.junit)
}