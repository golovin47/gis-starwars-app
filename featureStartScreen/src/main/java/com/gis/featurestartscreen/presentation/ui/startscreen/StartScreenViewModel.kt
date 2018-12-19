package com.gis.featurestartscreen.presentation.ui.startscreen

import com.gis.featurestartscreen.presentation.ui.startscreen.StartScreenStateChange.Error
import com.gis.utils.BaseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class StartScreenViewModel(
  private var goToMain: (() -> Unit)?
) :
  BaseViewModel<StartScreenState>() {

  override fun initState(): StartScreenState = StartScreenState()

  override fun vmIntents(): Observable<Any> =
    Observable.just(Any())
      .delay(3000, TimeUnit.MILLISECONDS)
      .doOnNext { goToMain?.invoke() }
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())

  override fun viewIntents(intentStream: Observable<*>): Observable<Any> =
    Observable.never()

  override fun reduceState(
    previousState: StartScreenState,
    stateChange: Any
  ): StartScreenState = StartScreenState()

  override fun onCleared() {
    goToMain = null
    super.onCleared()
  }
}