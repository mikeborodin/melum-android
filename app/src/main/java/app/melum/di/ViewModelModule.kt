package app.melum.di

import app.melum.ui.album.AlbumDetailsViewModel
import app.melum.ui.artist.ArtistDetailsViewModel
import app.melum.ui.explore.ExploreViewModel
import app.melum.ui.home.HomeViewModel
import app.melum.ui.main.MainViewModel
import app.melum.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModuleModule = module {
    viewModel { MainViewModel() }
    viewModel { SplashViewModel() }
    viewModel { HomeViewModel(get()) }
    viewModel { ExploreViewModel(get()) }
    viewModel { ArtistDetailsViewModel(get(), get()) }
    viewModel { AlbumDetailsViewModel(get(), get()) }
}