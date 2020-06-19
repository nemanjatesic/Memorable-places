package rs.raf.projekat3.nemanja_tesic_rn3017.modules

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import rs.raf.projekat3.nemanja_tesic_rn3017.data.db.ProjectDatabase
import java.util.*

val coreModule = module {

    single<SharedPreferences> {
        androidApplication().getSharedPreferences(androidApplication().packageName, Context.MODE_PRIVATE)
    }

    single { Room.databaseBuilder(androidContext(), ProjectDatabase::class.java, "ProjectDb")
        .fallbackToDestructiveMigration()
        .build() }

    single { createMoshi() }
}

fun createMoshi(): Moshi {
    return Moshi.Builder()
        .add(Date::class.java, Rfc3339DateJsonAdapter())
        .build()
}