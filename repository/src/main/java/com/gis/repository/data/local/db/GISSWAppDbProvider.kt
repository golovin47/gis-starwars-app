package com.gis.repository.data.local.db

import android.content.Context
import androidx.room.Room

class GISSWAppDbProvider {

  companion object {

    fun createDb(context: Context): GISSWAppDb {
      return Room.databaseBuilder(context, GISSWAppDb::class.java, "GISSWAppDb")
        .allowMainThreadQueries()
        .build()
    }
  }
}