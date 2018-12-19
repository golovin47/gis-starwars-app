package com.gis.repository.data.remote.entity.response

import com.bluelinelabs.logansquare.annotation.JsonField
import com.bluelinelabs.logansquare.annotation.JsonObject
import com.gis.repository.data.remote.entity.CharacterR

@JsonObject
data class CharactersResponse(
  @JsonField var results: List<CharacterR> = emptyList()
)