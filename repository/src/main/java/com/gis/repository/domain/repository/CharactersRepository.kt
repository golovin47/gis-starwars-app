package com.gis.repository.domain.repository

import com.gis.repository.domain.entitiy.Character
import io.reactivex.Completable
import io.reactivex.Observable

interface CharactersRepository {

  fun getCharacter(name: String): Observable<Character>

  fun findByName(name: String): Observable<List<Character>>

  fun insert(characters: List<Character>): Completable
}