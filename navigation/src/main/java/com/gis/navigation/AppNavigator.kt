package com.gis.navigation

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavOptions

class AppNavigator {

  private lateinit var navController: NavController

  fun setNavController(navController: NavController) {
    this.navController = navController
  }

  fun goToCharactersFromStartScreen() {
    val navOptions = NavOptions.Builder()
      .setEnterAnim(R.anim.anim_fade_in)
      .setExitAnim(R.anim.anim_fade_out)
      .setPopEnterAnim(R.anim.anim_fade_in)
      .setPopExitAnim(R.anim.anim_fade_out)
      .setPopUpTo(R.id.startScreenFragment, true)
      .build()

    navController.navigate(R.id.from_start_to_characters, null, navOptions)
  }

  fun goToCharacterDetailFromCharactersListScreen(name: String) {
    val navOptions = NavOptions.Builder()
      .setEnterAnim(R.anim.anim_fade_in)
      .setExitAnim(R.anim.anim_fade_out)
      .setPopEnterAnim(R.anim.anim_fade_in)
      .setPopExitAnim(R.anim.anim_fade_out)
      .build()

    val args = Bundle().apply { putString("name", name) }

    navController.navigate(R.id.from_characters_to_character_detail, args, navOptions)
  }
}