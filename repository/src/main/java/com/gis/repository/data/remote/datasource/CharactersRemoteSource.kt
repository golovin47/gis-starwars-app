package com.gis.repository.data.remote.datasource

import com.gis.repository.data.remote.api.GISSWAppApi
import com.gis.repository.data.remote.entity.CharacterR
import com.gis.repository.domain.datasource.CharactersDataSource
import com.gis.repository.domain.entitiy.Character
import io.reactivex.Completable
import io.reactivex.Observable

class CharactersRemoteSource(private val api: GISSWAppApi) : CharactersDataSource {

  override fun getCharacter(name: String): Observable<Character> =
    throw java.lang.UnsupportedOperationException("Remote data source doesn't support getCharacter()")

  override fun findByName(name: String): Observable<List<Character>> =
    api.findCharacters(name)
      .map { response ->
        if (response.results.isNotEmpty()) response.results.map { it.toDomain() }
        else emptyList()
      }

  override fun insert(characters: List<Character>): Completable =
    throw UnsupportedOperationException("RemoteSource doesn't handle insert")

  private fun CharacterR.toDomain() =
    Character(
      birthYear = birthYear,
      eyeColor = eyeColor,
      films = films,
      gender = gender,
      hairColor = hairColor,
      height = height,
      homeworld = homeworld,
      mass = mass,
      name = name,
      skinColor = skinColor,
      created = created,
      edited = edited,
      species = species,
      starships = starships,
      url = url,
      vehicles = vehicles
    )
}