package br.com.marvelapp.view

import android.os.Bundle
import android.os.Parcelable
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.marvelapp.R
import br.com.marvelapp.adapter.CharacterAdapter
import io.reactivex.android.schedulers.AndroidSchedulers

class CharactersActivity : AppCompatActivity() {

    private val viewModel: CharactersViewModel by lazy {
        ViewModelProviders.of(this).get(CharactersViewModel::class.java)
    }

    private val adapter: CharacterAdapter by lazy {
        CharacterAdapter()
    }

    private var recyclerState: Parcelable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_characters)

        initRecycler()
    }

    private fun initRecycler() {
        val linearLayoutManager = LinearLayoutManager(this)
        subscribeToList()
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState?.putParcelable("lmState", recyclerState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        recyclerState = savedInstanceState?.getParcelable("lmState")
    }

    private fun subscribeToList() {
        val disposable = viewModel.characterList
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list ->
                    adapter.submitList(list)
                    if (recyclerState != null) {
                        recyclerState = null
                    }
                },
                { e ->
                    Log.e("NGVL", "Error", e)
                }
            )
    }
}