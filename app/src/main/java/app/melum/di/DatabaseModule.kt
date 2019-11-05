package app.melum.di

import app.melum.data.database.AlbumsDatabase
import io.realm.RealmConfiguration
import org.koin.dsl.module

val databaseModule = module {
    single {
        RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .name("melum.realm")
            .build()
    }

    factory {
        AlbumsDatabase(get())
    }
}