package com.gis.repository.domain.interactors

import com.gis.repository.domain.entitiy.Character
import com.gis.repository.domain.repository.CharactersRepository

class GetCharacterByNameUseCase(private val repository: CharactersRepository) {
  fun execute(name: String) = repository.getCharacter(name)
}


class SearchCharactersByNameUseCase(private val repository: CharactersRepository) {

  fun execute(name: String) = repository.findByName(name)
}


class InsertCharactersUseCase(private val repository: CharactersRepository) {

  fun execute(characters: List<Character>) = repository.insert(characters)
}