package com.example.projectexam.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectexam.BuildConfig
import com.example.projectexam.data.source.HomeDatasource
import com.example.projectexam.domain.entity.LatestGameEntity
import com.example.projectexam.presentation.LatestGameHomeView
import com.example.projectexam.presentation.state.LatestGameState
import io.reactivex.disposables.CompositeDisposable


class LatestGameViewModel(private val datasource: HomeDatasource) : ViewModel(),
    LatestGameHomeView {

    private val disposables = CompositeDisposable()
    private val observer = MutableLiveData<LatestGameState>()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    override fun onShowLoading() {
        TODO("Not yet implemented")
    }

    override fun onHideLoading() {
        TODO("Not yet implemented")
    }

    override fun onSuccess(entity: LatestGameEntity) {
        TODO("Not yet implemented")
    }

    override fun onError(error: Throwable) {
        TODO("Not yet implemented")
    }

    override fun onPaginationSuccess(entity: LatestGameEntity) {
        TODO("Not yet implemented")
    }

    override fun onPaginationError(error: Throwable) {
        TODO("Not yet implemented")
    }

    override fun getApiLatest() {
        datasource.getApiLatest(BuildConfig.API_KEY)
            .map<LatestGameState>(LatestGameState::Success)
            .onErrorReturn(LatestGameState::Error)
            .toFlowable()
            .startWith(LatestGameState.Loading)
            .subscribe(observer::postValue)
            .let(disposables::add)
    }

    override val latestGameState: LiveData<LatestGameState>
        get() = observer

}