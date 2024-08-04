import app.buildlogic.convention.configureKotlinJvm
import app.buildlogic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class JvmKtorConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {

        target.run {
            pluginManager.run {
                apply("org.jetbrains.kotlin.plugin.serialization")
                configureKotlinJvm()
            }
            dependencies{
                "implementation"(libs.findBundle("ktor").get())
            }
        }
    }
}