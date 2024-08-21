plugins {
    alias(libs.plugins.runbuddy.android.library)
    alias(libs.plugins.runbuddy.android.room)
}

android {
    namespace = "app.core.database"
}

dependencies {


    implementation(libs.bundles.koin)
    implementation(projects.core.domain)
    implementation(libs.org.mongodb.bson)

    implementation(libs.androidx.espresso.core)
    implementation(libs.androidx.junit)
}