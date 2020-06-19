package rs.raf.projekat3.nemanja_tesic_rn3017.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.projekat3.nemanja_tesic_rn3017.data.db.ProjectDatabase
import rs.raf.projekat3.nemanja_tesic_rn3017.data.repositories.PlaceRepository
import rs.raf.projekat3.nemanja_tesic_rn3017.data.repositories.PlaceRepositoryImpl
import rs.raf.projekat3.nemanja_tesic_rn3017.presentation.viewmodel.PlaceViewModel

val placeModule = module {

    viewModel { PlaceViewModel(get()) }

    single<PlaceRepository> { PlaceRepositoryImpl(get()) }

    single { get<ProjectDatabase>().getPlaceDao() }
}