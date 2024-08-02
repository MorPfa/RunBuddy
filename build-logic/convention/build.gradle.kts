plugins {
    `kotlin-dsl`
}

group = "app.buildlogic"

dependencies {

    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)

}

gradlePlugin {
    plugins {
        register("androidApplication"){
            id = "runbuddy.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
    }
}