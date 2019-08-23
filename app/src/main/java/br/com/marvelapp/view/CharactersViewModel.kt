package br.com.marvelapp.view

import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import br.com.marvelapp.padding.CharacterDataSourceFactory
import br.com.marvelapp.model.Character
import br.com.marvelapp.service.MarvelApi
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CharactersViewModel : ViewModel() {

    var characterList: Observable<PagedList<Character>>

    private val compositeDisposable = CompositeDisposable()

    private val pagedSize = 20

    private val sourceFactory: CharacterDataSourceFactory

    init {
        sourceFactory = CharacterDataSourceFactory(
            compositeDisposable,
            MarvelApi.getService()
        )

        val config = PagedList.Config.Builder()
            .setPageSize(pagedSize)
            .setInitialLoadSizeHint(pagedSize * 3)
            .setPrefetchDistance(10)
            .setEnablePlaceholders(false)
            .build()

        characterList = RxPagedListBuilder(sourceFactory, config)
            .setFetchScheduler(Schedulers.io())
            .buildObservable()
            .cache()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}