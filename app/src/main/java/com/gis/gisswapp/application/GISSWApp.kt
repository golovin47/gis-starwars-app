package com.gis.gisswapp.application

import android.app.Application
import com.gis.featurecharacters.presentation.di.charactersModule
import com.gis.featurestartscreen.presentation.di.startScreenModule
import com.gis.navigation.di.navigationModule
import com.gis.repository.di.repoModule
import com.gis.utils.di.utilsModule
import org.koin.android.ext.android.startKoin

class GISSWApp : Application() {

  override fun onCreate() {
    super.onCreate()

    startKoin(
      this,
      listOf(navigationModule, repoModule, utilsModule,
        startScreenModule, charactersModule)
    )
  }
}