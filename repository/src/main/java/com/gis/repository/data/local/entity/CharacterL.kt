package com.gis.repository.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class CharacterL(
  var birthYear: String = "",
  var eyeColor: String = "",
  var films: List<String> = emptyList(),
  var gender: String = "",
  var hairColor: String = "",
  var height: String = "",
  var homeworld: String = "",
  var mass: String = "",
  @PrimaryKey var name: String = "",
  var skinColor: String = "",
  var created: Date = Date(),
  var edited: Date = Date(),
  var species: List<String> = emptyList(),
  var starships: List<String> = emptyList(),
  var url: String = "",
  var vehicles: List<String> = emptyList()
)