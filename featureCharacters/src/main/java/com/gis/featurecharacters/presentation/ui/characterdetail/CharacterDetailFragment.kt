package com.gis.featurecharacters.presentation.ui.characterdetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.gis.featurecharacters.R
import com.gis.featurecharacters.databinding.FragmentCharacterDetailBinding
import com.gis.featurecharacters.presentation.ui.characterdetail.CharacterDetailIntent.GetCharacter
import com.gis.utils.BaseView
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Observable
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterDetailFragment : Fragment(), BaseView<CharacterDetailState> {

  private var binding: FragmentCharacterDetailBinding? = null
  private val vmCharacterDetail: CharacterDetailViewModel by viewModel()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

    initBinding(inflater, container)
    initIntents()

    return binding!!.root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    handleStates()
  }

  override fun onDestroyView() {
    binding = null
    super.onDestroyView()
  }

  private fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_character_detail, container, false)
  }

  @SuppressLint("CheckResult")
  override fun initIntents() {
    Observable.just(GetCharacter(arguments!!.getString("name", "")))
      .subscribe(vmCharacterDetail.viewIntentsConsumer())
  }

  override fun handleStates() {
    vmCharacterDetail.stateReceived().observe(this, Observer { state -> render(state) })
  }

  override fun render(state: CharacterDetailState) {
    binding?.loading = state.loading
    binding?.character = state.character

    if (state.error != null)
      Snackbar.make(view!!, state.error.message!!, Snackbar.LENGTH_SHORT).show()
  }
}