package com.gis.repository.data.remote.entity

import com.bluelinelabs.logansquare.annotation.JsonField
import com.bluelinelabs.logansquare.annotation.JsonObject
import com.gis.repository.data.remote.api.ISO8601DateConverter
import java.util.*

@JsonObject
data class CharacterR(
  @JsonField(name = ["birth_year"]) var birthYear: String = "",
  @JsonField(name = ["eye_color"]) var eyeColor: String = "",
  @JsonField(name = ["films"]) var films: List<String> = emptyList(),
  @JsonField(name = ["gender"]) var gender: String = "",
  @JsonField(name = ["hair_color"]) var hairColor: String = "",
  @JsonField(name = ["height"]) var height: String = "",
  @JsonField(name = ["homeworld"]) var homeworld: String = "",
  @JsonField(name = ["mass"]) var mass: String = "",
  @JsonField(name = ["name"]) var name: String = "",
  @JsonField(name = ["skin_color"]) var skinColor: String = "",
  @JsonField(name = ["created"], typeConverter = ISO8601DateConverter::class) var created: Date = Date(),
  @JsonField(name = ["edited"], typeConverter = ISO8601DateConverter::class) var edited: Date = Date(),
  @JsonField(name = ["species"]) var species: List<String> = emptyList(),
  @JsonField(name = ["starships"]) var starships: List<String> = emptyList(),
  @JsonField(name = ["url"]) var url: String = "",
  @JsonField(name = ["vehicles"]) var vehicles: List<String> = emptyList()
)