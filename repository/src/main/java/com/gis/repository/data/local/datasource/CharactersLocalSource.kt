package com.gis.repository.data.local.datasource

import com.gis.repository.data.local.db.CharactersDao
import com.gis.repository.data.local.entity.CharacterL
import com.gis.repository.domain.datasource.CharactersDataSource
import com.gis.repository.domain.entitiy.Character
import io.reactivex.Completable
import io.reactivex.Observable

class CharactersLocalSource(private val charactersDao: CharactersDao) :
  CharactersDataSource {

  override fun getCharacter(name: String): Observable<Character> =
    charactersDao.getCharacter(name)
      .map { list -> if (list.isNotEmpty()) list[0].toDomain() else Character.Empty }
      .toObservable()

  override fun findByName(name: String): Observable<List<Character>> =
    charactersDao.find(name)
      .map { if (it.isEmpty()) emptyList() else it.map { item -> item.toDomain() } }
      .toObservable()

  override fun insert(characters: List<Character>): Completable = Completable.fromAction {
    charactersDao.insertAll(characters.map { it.toLocal() })
  }

  private fun CharacterL.toDomain() =
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

  private fun Character.toLocal() =
    CharacterL(
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