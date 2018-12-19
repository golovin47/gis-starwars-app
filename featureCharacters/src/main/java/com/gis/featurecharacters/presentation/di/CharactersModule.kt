package com.gis.featurecharacters.presentation.di

import com.gis.featurecharacters.presentation.ui.characterdetail.CharacterDetailViewModel
import com.gis.featurecharacters.presentation.ui.characterslist.CharactersViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val charactersModule = module {

  viewModel { CharactersViewModel(get(), get(name = "fromCharactersToCharacterDetail")) }

  viewModel { CharacterDetailViewModel(get()) }
}