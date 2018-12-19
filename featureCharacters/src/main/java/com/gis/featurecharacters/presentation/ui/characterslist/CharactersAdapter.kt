package com.gis.featurecharacters.presentation.ui.characterslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gis.featurecharacters.R
import com.gis.featurecharacters.databinding.ItemCharactersBinding
import com.gis.featurecharacters.databinding.ItemCharactersLoadingBinding
import com.gis.featurecharacters.presentation.ui.characterslist.CharactersIntent.GoToCharacterDetail
import com.gis.featurecharacters.presentation.ui.characterslist.CharactersItem.DefaultCharacterItem
import com.gis.repository.domain.entitiy.Character
import com.gis.utils.domain.ImageLoader
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.subjects.Subject
import java.util.concurrent.TimeUnit


class CharactersAdapter(
  private val itemClickPublisher: Subject<GoToCharacterDetail>
) : ListAdapter<CharactersItem, RecyclerView.ViewHolder>(object :
  DiffUtil.ItemCallback<CharactersItem>() {

  override fun areItemsTheSame(oldItem: CharactersItem, newItem: CharactersItem): Boolean =
    when {
      oldItem is DefaultCharacterItem && newItem is DefaultCharacterItem ->
        oldItem.character.name == newItem.character.name

      else -> oldItem === newItem
    }

  override fun areContentsTheSame(oldItem: CharactersItem, newItem: CharactersItem): Boolean =
    oldItem == newItem

}) {

  private val DEFAULT_ITEM = 0x099
  private val LOADING_ITEM = 0x098

  override fun onFailedToRecycleView(holder: RecyclerView.ViewHolder): Boolean = true

  override fun getItemViewType(position: Int): Int =
    if (getItem(position) is DefaultCharacterItem) DEFAULT_ITEM else LOADING_ITEM


  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =

    if (viewType == DEFAULT_ITEM) {
      val binding = DataBindingUtil.inflate<ItemCharactersBinding>(
        LayoutInflater.from(parent.context),
        R.layout.item_characters,
        parent,
        false
      )
      CharacterViewHolder(binding)
    } else {
      val binding = DataBindingUtil.inflate<ItemCharactersLoadingBinding>(
        LayoutInflater.from(parent.context),
        R.layout.item_characters_loading,
        parent,
        false
      )
      LoadingViewHolder(binding)
    }


  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    if (holder is CharacterViewHolder) {
      val character = (getItem(position) as DefaultCharacterItem).character

      RxView.clicks(holder.binding.tvCatId)
        .skip(300, TimeUnit.MILLISECONDS)
        .map { GoToCharacterDetail(character.name) }
        .subscribe(itemClickPublisher)

      holder.bind(character)
    }
  }
}


class CharacterViewHolder(val binding: ItemCharactersBinding) :
  RecyclerView.ViewHolder(binding.root) {

  fun bind(cat: Character) {
    binding.tvCatId.text = cat.name
  }
}


class LoadingViewHolder(binding: ItemCharactersLoadingBinding) : RecyclerView.ViewHolder(binding.root)