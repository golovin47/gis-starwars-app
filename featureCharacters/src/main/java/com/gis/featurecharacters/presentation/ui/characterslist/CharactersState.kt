package com.gis.featurecharacters.presentation.ui.characterslist

import com.gis.repository.domain.entitiy.Character


data class CharactersState(
  val loading: Boolean = false,
  val characters: List<Character> = emptyList(),
  val error: Throwable? = null
)


sealed class CharactersIntent {
  class SearchByName(val name: String) : CharactersIntent()
  object EmptySearch : CharactersIntent()
  class GoToCharacterDetail(val name: String) : CharactersIntent()
}


sealed class CharactersStateChange {
  object StartLoading : CharactersStateChange()
  class CharactersReceived(val characters: List<Character>) : CharactersStateChange()
  class Error(val error: Throwable) : CharactersStateChange()
  object HideError : CharactersStateChange()
}