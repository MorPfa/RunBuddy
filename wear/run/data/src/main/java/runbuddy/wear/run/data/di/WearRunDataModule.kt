package runbuddy.wear.run.data.di


import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import runbuddy.wear.run.data.HealthServiceExerciseTracker
import runbuddy.wear.run.data.WatchToPhoneConnector
import runbuddy.wear.run.domain.ExerciseTracker
import runbuddy.wear.run.domain.PhoneConnector
import runbuddy.wear.run.domain.RunningTracker

val wearRunDataModule = module {
    singleOf(::HealthServiceExerciseTracker).bind<ExerciseTracker>()
    singleOf(::WatchToPhoneConnector).bind<PhoneConnector>()
    singleOf(::RunningTracker)
}