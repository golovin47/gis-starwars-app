package com.gis.repository.domain.entitiy

import java.util.*

data class Character(
  var birthYear: String = "",
  var eyeColor: String = "",
  var films: List<String> = emptyList(),
  var gender: String = "",
  var hairColor: String = "",
  var height: String = "",
  var homeworld: String = "",
  var mass: String = "",
  var name: String = "",
  var skinColor: String = "",
  var created: Date = Date(),
  var edited: Date = Date(),
  var species: List<String> = emptyList(),
  var starships: List<String> = emptyList(),
  var url: String = "",
  var vehicles: List<String> = emptyList()
) {

  companion object {
    val Empty = Character()
  }
}