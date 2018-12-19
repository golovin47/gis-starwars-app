package com.gis.featurecharacters.presentation.ui.characterdetail

import com.gis.featurecharacters.presentation.ui.characterdetail.CharacterDetailIntent.GetCharacter
import com.gis.featurecharacters.presentation.ui.characterdetail.CharacterDetailStateChange.*
import com.gis.repository.domain.interactors.GetCharacterByNameUseCase
import com.gis.utils.BaseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CharacterDetailViewModel(private val getCharacterByNameUseCase: GetCharacterByNameUseCase) :
  BaseViewModel<CharacterDetailState>() {

  override fun initState(): CharacterDetailState = CharacterDetailState()

  override fun viewIntents(intentStream: Observable<*>): Observable<Any> =
    intentStream.ofType(GetCharacter::class.java)
      .switchMap { event ->
        getCharacterByNameUseCase.execute(event.name)
          .map { character -> CharacterReceived(character) }
          .cast(CharacterDetailStateChange::class.java)
          .startWith(StartLoading)
          .onErrorResumeNext { e: Throwable -> Observable.just(Error(e), HideError) }
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
      }

  override fun reduceState(previousState: CharacterDetailState, stateChange: Any): CharacterDetailState =
    when (stateChange) {

      is StartLoading -> previousState.copy(loading = true, error = null)

      is CharacterReceived -> previousState.copy(
        loading = false,
        character = stateChange.character,
        error = null
      )

      is Error -> previousState.copy(loading = false, error = stateChange.error)

      is HideError -> previousState.copy(error = null)

      else -> previousState
    }
}