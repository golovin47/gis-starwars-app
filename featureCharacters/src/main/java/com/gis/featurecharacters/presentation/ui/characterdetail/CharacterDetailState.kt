package com.gis.featurecharacters.presentation.ui.characterdetail

import com.gis.repository.domain.entitiy.Character

data class CharacterDetailState(
  val loading: Boolean = false,
  val character: Character = Character.Empty,
  val error: Throwable? = null
)


sealed class CharacterDetailIntent {
  class GetCharacter(val name: String) : CharacterDetailIntent()
}


sealed class CharacterDetailStateChange {
  object StartLoading : CharacterDetailStateChange()
  class CharacterReceived(val character: Character) : CharacterDetailStateChange()
  class Error(val error: Throwable) : CharacterDetailStateChange()
  object HideError : CharacterDetailStateChange()
}