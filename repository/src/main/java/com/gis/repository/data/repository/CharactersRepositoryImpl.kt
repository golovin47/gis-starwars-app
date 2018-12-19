package com.gis.repository.data.repository

import com.gis.repository.domain.datasource.CharactersDataSource
import com.gis.repository.domain.entitiy.Character
import com.gis.repository.domain.repository.CharactersRepository
import io.reactivex.Completable
import io.reactivex.Observable

class CharactersRepositoryImpl(
  private val localSourceCharacters: CharactersDataSource,
  private val remoteSourceCharacters: CharactersDataSource
) :
  CharactersRepository {

  override fun getCharacter(name: String): Observable<Character> =
    localSourceCharacters.getCharacter(name)

  override fun findByName(name: String): Observable<List<Character>> =
    localSourceCharacters.findByName(name)
      .switchMap { localList ->
        if (localList.isEmpty()) findCharactersInRemote(name)
        else Observable.just(localList)
      }

  private fun findCharactersInRemote(name: String) =
    remoteSourceCharacters.findByName(name)
      .doOnNext { if (it.isNotEmpty()) insert(it).subscribe() }

  override fun insert(characters: List<Character>): Completable =
    localSourceCharacters.insert(characters)
}