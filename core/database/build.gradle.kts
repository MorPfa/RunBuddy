plugins {
    alias(libs.plugins.runbuddy.android.library)
}

android {
    namespace = "app.core.database"
}

dependencies {

    implementation(projects.core.domain)
    implementation(libs.org.mongodb.bson)
    implementation(libs.androidx.espresso.core)
    implementation(libs.androidx.junit)
}