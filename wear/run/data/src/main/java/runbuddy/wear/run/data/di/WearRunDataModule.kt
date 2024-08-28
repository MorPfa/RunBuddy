package runbuddy.wear.run.data.di


import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import runbuddy.wear.run.data.HealthServiceExerciseTracker
import runbuddy.wear.run.domain.ExerciseTracker

val wearRunDataModule = module {
    singleOf(::HealthServiceExerciseTracker).bind<ExerciseTracker>()
}