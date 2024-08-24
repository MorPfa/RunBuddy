plugins {
    alias(libs.plugins.runbuddy.android.dynamic.feature)
}
android {
    namespace = "app.runbuddy.analytics.analytics_feature"
}

dependencies {
    implementation(project(":app"))

    implementation(libs.androidx.navigation.compose)
    implementation(projects.analytics.domain)
    implementation(projects.analytics.data)
    implementation(projects.core.database)
    api(projects.analytics.presentation)

}