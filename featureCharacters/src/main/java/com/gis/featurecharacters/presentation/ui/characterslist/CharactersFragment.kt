package com.gis.featurecharacters.presentation.ui.characterslist

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gis.featurecharacters.R
import com.gis.featurecharacters.databinding.FragmentCharactersBinding
import com.gis.featurecharacters.presentation.ui.characterslist.CharactersIntent.*
import com.gis.utils.BaseView
import com.gis.utils.domain.ImageLoader
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class CharactersFragment : Fragment(), BaseView<CharactersState> {

  private val itemClickPublisher = PublishSubject.create<GoToCharacterDetail>()

  private lateinit var viewSubscriptions: Disposable
  private var binding: FragmentCharactersBinding? = null
  private val vmCharacters: CharactersViewModel by viewModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    handleStates()
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    initBinding(inflater, container)
    initRecyclerView(container!!.context)
    initIntents()

    return binding!!.root
  }

  override fun onDestroyView() {
    binding = null
    viewSubscriptions.dispose()
    super.onDestroyView()
  }

  private fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_characters, container, false)
  }

  private fun initRecyclerView(context: Context) {
    val adapter = CharactersAdapter(itemClickPublisher)
    binding!!.rvCharacters
      .apply {
        layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        setAdapter(adapter)
      }
  }

  @SuppressLint("CheckResult")
  override fun initIntents() {
    viewSubscriptions = Observable.merge(listOf(

      RxTextView.textChanges(binding!!.etSearch)
        .debounce(300, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
        .map { query -> if (query.isBlank()) EmptySearch else SearchByName(query.toString()) }
        .skip(1),

      itemClickPublisher
    ))
      .subscribe(vmCharacters.viewIntentsConsumer())
  }

  override fun handleStates() {
    vmCharacters.stateReceived().observe(this, Observer { state -> render(state) })
  }

  override fun render(state: CharactersState) {

    if (state.error != null)
      Snackbar.make(view!!, state.error.message!!, Snackbar.LENGTH_SHORT).show()

    (binding!!.rvCharacters.adapter as CharactersAdapter).submitList(state.characters)
  }
}