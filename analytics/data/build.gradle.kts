plugins {
    alias(libs.plugins.runbuddy.android.library)
}

android {
    namespace = "app.runbuddy.analytics.data"
}

dependencies {


    implementation(libs.kotlinx.coroutines.core)
    implementation(projects.core.database)
    implementation(projects.core.domain)
    implementation(projects.analytics.domain)

}