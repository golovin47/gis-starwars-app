package com.gis.featurestartscreen.presentation.di

import com.gis.featurestartscreen.presentation.ui.startscreen.StartScreenViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val startScreenModule = module {

  viewModel { StartScreenViewModel(get("fromStartToCharacters")) }
}