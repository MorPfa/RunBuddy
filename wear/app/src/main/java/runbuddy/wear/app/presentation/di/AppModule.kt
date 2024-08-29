package runbuddy.wear.app.presentation.di

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import runbuddy.wear.app.presentation.RunBuddyApp

val appModule = module {
    single {
        (androidApplication() as RunBuddyApp).applicationScope
    }
}