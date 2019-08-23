package br.com.marvelapp.padding

import androidx.paging.DataSource
import br.com.marvelapp.model.Character
import br.com.marvelapp.service.MarvelApi
import io.reactivex.disposables.CompositeDisposable

class CharacterDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val marvelApi: MarvelApi
) : DataSource.Factory<Int, Character>() {

    override fun create(): DataSource<Int, Character> {
        return CharactersDataSource(marvelApi, compositeDisposable)
    }
}