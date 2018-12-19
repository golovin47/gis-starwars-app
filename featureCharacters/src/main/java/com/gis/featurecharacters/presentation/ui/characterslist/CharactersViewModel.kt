package com.gis.featurecharacters.presentation.ui.characterslist

import com.gis.featurecharacters.presentation.ui.characterslist.CharactersItem.DefaultCharacterItem
import com.gis.featurecharacters.presentation.ui.characterslist.CharactersItem.LoadingCharacterItem
import com.gis.repository.domain.entitiy.Character
import com.gis.repository.domain.interactors.SearchCharactersByNameUseCase
import com.gis.utils.BaseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CharactersViewModel(
  private val searchCharactersByNameUseCase: SearchCharactersByNameUseCase,
  private var goToCharacterDetail: ((String) -> Unit)?
) : BaseViewModel<CharactersState>() {

  override fun initState(): CharactersState =
    CharactersState()

  override fun viewIntents(intentStream: Observable<*>): Observable<Any> =
    Observable.merge(listOf(

      intentStream.ofType(CharactersIntent.SearchByName::class.java)
        .switchMap { event ->
          searchCharactersByNameUseCase.execute(event.name)
            .map { list -> CharactersStateChange.CharactersReceived(list.map { item -> item.toPresentation() }) }
            .cast(CharactersStateChange::class.java)
            .startWith(CharactersStateChange.StartLoading)
            .onErrorResumeNext { e: Throwable -> handleError(e) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        },

      intentStream.ofType(CharactersIntent.EmptySearch::class.java)
        .map {
          CharactersStateChange.CharactersReceived(
            emptyList()
          )
        },

      intentStream.ofType(CharactersIntent.GoToCharacterDetail::class.java)
        .doOnNext { event -> goToCharacterDetail?.invoke(event.name) }
    ))

  private fun handleError(e: Throwable) =
    Observable.just(
      CharactersStateChange.Error(e),
      CharactersStateChange.HideError
    )

  override fun reduceState(
    previousState: CharactersState,
    stateChange: Any
  ): CharactersState =
    when (stateChange) {

      is CharactersStateChange.StartLoading -> previousState.copy(
        loading = true,
        characters = listOf(LoadingCharacterItem),
        error = null
      )

      is CharactersStateChange.CharactersReceived -> previousState.copy(
        loading = false,
        characters = stateChange.characters,
        error = null
      )

      is CharactersStateChange.Error -> previousState.copy(
        loading = false,
        characters = emptyList(),
        error = stateChange.error
      )

      is CharactersStateChange.HideError -> previousState.copy(error = null)

      else -> previousState
    }

  override fun onCleared() {
    goToCharacterDetail = null
    super.onCleared()
  }

  private fun Character.toPresentation() = DefaultCharacterItem(this)
}