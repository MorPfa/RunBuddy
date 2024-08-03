import app.buildlogic.convention.addUiLayerDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureUiConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.apply("runbuddy.android.application")
            pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

          dependencies{
            addUiLayerDependencies(target)
          }
        }
    }
}