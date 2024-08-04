plugins {
    alias(libs.plugins.runbuddy.android.library)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.runbuddy.jvm.ktor)


}

android {
    namespace = "app.auth.data"
}

dependencies {

    implementation(projects.auth.domain)
    implementation(projects.core.domain)
    implementation(projects.core.data)

    implementation(libs.androidx.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}