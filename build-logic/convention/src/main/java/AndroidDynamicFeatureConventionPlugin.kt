import app.buildlogic.convention.ExtensionType
import app.buildlogic.convention.addUiLayerDependencies
import app.buildlogic.convention.configureAndroidCompose
import app.buildlogic.convention.configureBuildTypes
import app.buildlogic.convention.configureKotlinAndroid
import com.android.build.api.dsl.DynamicFeatureExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidDynamicFeatureConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.android.dynamic-feature")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<DynamicFeatureExtension> {

                configureKotlinAndroid(this)
                configureAndroidCompose(this)
                configureBuildTypes(this, extensionType = ExtensionType.DYNAMIC_FEATURE)
            }
            dependencies {
                addUiLayerDependencies(target)
                "testImplementation"(kotlin("test"))
            }
        }
    }
}