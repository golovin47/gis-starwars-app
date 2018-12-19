package com.gis.repository.data.remote.api

import com.gis.repository.data.remote.entity.CharacterR
import com.gis.repository.data.remote.entity.response.CharactersResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GISSWAppApi {

  @GET("people/")
  fun findCharacters(@Query("search") name: String): Observable<CharactersResponse>
}