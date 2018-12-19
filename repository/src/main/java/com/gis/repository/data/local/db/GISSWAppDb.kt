package com.gis.repository.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gis.repository.data.local.entity.CharacterL

@Database(entities = [CharacterL::class], version = 1, exportSchema = false)
@TypeConverters(DbTypeConverters::class)
abstract class GISSWAppDb : RoomDatabase() {

  abstract fun charactersDao(): CharactersDao

}