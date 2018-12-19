package com.gis.repository.di

import android.content.Context.MODE_PRIVATE
import com.gis.repository.data.local.datasource.CharactersLocalSource
import com.gis.repository.data.local.db.GISSWAppDbProvider
import com.gis.repository.data.remote.api.GISSWAppApiProvider
import com.gis.repository.data.remote.datasource.CharactersRemoteSource
import com.gis.repository.data.repository.CharactersRepositoryImpl
import com.gis.repository.domain.datasource.CharactersDataSource
import com.gis.repository.domain.interactors.GetCharacterByNameUseCase
import com.gis.repository.domain.interactors.InsertCharactersUseCase
import com.gis.repository.domain.interactors.SearchCharactersByNameUseCase
import com.gis.repository.domain.repository.CharactersRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val repoModule = module {

  single { GISSWAppDbProvider.createDb(androidContext()).charactersDao() }

  single { GISSWAppApiProvider.createApi() }

  single { androidContext().getSharedPreferences("sharedPrefs", MODE_PRIVATE) }


  //Character
  single<CharactersDataSource>("local") { CharactersLocalSource(get()) }

  single<CharactersDataSource>("remote") { CharactersRemoteSource(get()) }

  single<CharactersRepository> { CharactersRepositoryImpl(get("local"), get("remote")) }

  factory { GetCharacterByNameUseCase(get()) }

  factory { SearchCharactersByNameUseCase(get()) }

  factory { InsertCharactersUseCase(get()) }
}