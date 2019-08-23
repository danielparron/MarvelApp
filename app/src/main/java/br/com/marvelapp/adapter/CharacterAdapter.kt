package br.com.marvelapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.marvelapp.R
import br.com.marvelapp.extensions.load
import br.com.marvelapp.model.Character
import kotlinx.android.synthetic.main.item_character.view.*

class CharacterAdapter : PagedListAdapter<Character, CharacterAdapter.ViewHolder>(characterDiff) {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.item_character, p0, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = getItem(position)
        holder.txtName.text = character?.name
        holder.imgThumbnail.load("${character?.thumbnail?.path}/standard_medium.${character?.thumbnail?.extension}")
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imgThumbnail = itemView.imgThumbnail
        val txtName = itemView.txtName
    }

    companion object {
        val characterDiff = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(old: Character, new: Character): Boolean {
                return old.id == new.id
            }

            override fun areContentsTheSame(old: Character, new: Character): Boolean {
                return old == new
            }
        }
    }
}
