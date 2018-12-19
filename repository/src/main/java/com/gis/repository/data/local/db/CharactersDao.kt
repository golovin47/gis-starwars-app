package com.gis.repository.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gis.repository.data.local.entity.CharacterL
import io.reactivex.Flowable

@Dao
interface CharactersDao {

  @Query("SELECT * FROM CharacterL WHERE name == :name")
  fun getCharacter(name: String): Flowable<List<CharacterL>>

  @Query("SELECT * FROM CharacterL WHERE name LIKE '%' || :name || '%'")
  fun find(name: String): Flowable<List<CharacterL>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(characters: List<CharacterL>): List<Long>
}