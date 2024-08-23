plugins {
    alias(libs.plugins.runbuddy.android.feature.ui)

}

android {
    namespace = "app.runbuddy.analytics.presentation"
}

dependencies {

    implementation(projects.analytics.domain)
}