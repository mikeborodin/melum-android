package app.melum.di

import app.melum.ui.MainViewModel
import app.melum.ui.album.AlbumDetailsViewModel
import app.melum.ui.artist.ArtistDetailsViewModel
import app.melum.ui.explore.ExploreViewModel
import app.melum.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModuleModule = module {
    viewModel { MainViewModel() }
    viewModel { HomeViewModel() }
    viewModel { ExploreViewModel() }
    viewModel { ArtistDetailsViewModel() }
    viewModel { AlbumDetailsViewModel() }
}