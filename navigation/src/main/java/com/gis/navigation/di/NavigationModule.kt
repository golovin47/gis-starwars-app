package com.gis.navigation.di

import com.gis.navigation.AppNavigator
import org.koin.dsl.module.module

val navigationModule = module {

  single(createOnStart = true) { AppNavigator() }

  factory(name = "fromStartToCharacters") { { get<AppNavigator>().goToCharactersFromStartScreen() } }
  factory(name = "fromCharactersToCharacterDetail") {
    { name: String -> get<AppNavigator>().goToCharacterDetailFromCharactersListScreen(name) }
  }
}