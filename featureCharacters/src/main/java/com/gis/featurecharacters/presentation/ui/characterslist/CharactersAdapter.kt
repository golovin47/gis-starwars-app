package com.gis.featurecharacters.presentation.ui.characterslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gis.featurecharacters.R
import com.gis.featurecharacters.databinding.ItemCharactersBinding
import com.gis.featurecharacters.presentation.ui.characterslist.CharactersIntent.GoToCharacterDetail
import com.gis.repository.domain.entitiy.Character
import com.gis.utils.domain.ImageLoader
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.subjects.Subject
import java.util.concurrent.TimeUnit

class CharactersAdapter(
  private val itemClickPublisher: Subject<GoToCharacterDetail>,
  private val imageLoader: ImageLoader
) : ListAdapter<Character, CharacterViewHolder>(object :
  DiffUtil.ItemCallback<Character>() {

  override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean =
    oldItem.name == newItem.name

  override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean =
    oldItem == newItem

}) {

  override fun onFailedToRecycleView(holder: CharacterViewHolder): Boolean = true

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {

    val binding = DataBindingUtil.inflate<ItemCharactersBinding>(
      LayoutInflater.from(parent.context),
      R.layout.item_characters,
      parent,
      false
    )
    return CharacterViewHolder(binding, imageLoader)
  }

  override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
    val character = getItem(position)

    RxView.clicks(holder.binding.tvCatId)
      .skip(300, TimeUnit.MILLISECONDS)
      .map { GoToCharacterDetail(character.name) }
      .subscribe(itemClickPublisher)

    holder.bind(character)
  }
}


class CharacterViewHolder(val binding: ItemCharactersBinding, val imageLoader: ImageLoader) :
  RecyclerView.ViewHolder(binding.root) {

  fun bind(cat: Character) {
    binding.tvCatId.text = cat.name
  }
}