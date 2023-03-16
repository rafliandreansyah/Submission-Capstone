package com.dicoding.submission_capstone.core.ui

import android.content.Context
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.TEXT_ALIGNMENT_CENTER
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submission_capstone.R
import com.dicoding.submission_capstone.core.domain.model.Game
import com.dicoding.submission_capstone.databinding.ItemGameBinding
import com.google.android.material.chip.Chip
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject

class GameAdapter @Inject constructor(@ActivityContext private val context: Context): RecyclerView.Adapter<GameAdapter.GamesViewHolder>() {

    private var listGame: List<Game>? = null
    private lateinit var listener: (id: Long) -> Unit
    fun setData(listGame: List<Game>) {
        this.listGame = listGame
    }

    fun setOnItemClickListener(listener: (id: Long) -> Unit){
        this.listener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GamesViewHolder {
        val itemBinding = ItemGameBinding.inflate(LayoutInflater.from(parent.context), parent, false);
        return GamesViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = if (listGame != null) listGame!!.size else 0

    override fun onBindViewHolder(holder: GamesViewHolder, position: Int) {
        if (listGame != null) {
            holder.bind(listGame!![position])
        }
    }

    inner class GamesViewHolder(private val binding: ItemGameBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(game: Game) {
            Glide.with(binding.root)
                .load(game.backgroundImage)
                .centerCrop()
                .into(binding.imgBgGames)

            binding.tvTitleGame.text = game.name
            val chipGroup = binding.chipGenre
            chipGroup.removeAllViews()
            game.genres?.forEach {
                val chip = Chip(context)
                chip.text = it
                chip.setChipBackgroundColorResource(R.color.colorPurpleLight)
                chip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10f)
                chip.setTextColor(ContextCompat.getColor(context, R.color.white))
                chip.textAlignment = TEXT_ALIGNMENT_CENTER
                chipGroup.addView(chip)
                chip.isClickable = false
            }

            val platforms = game.platforms.toString().trim().lowercase(Locale.getDefault())
            binding.icXbox.visibility = if (platforms.contains("xbox")) View.VISIBLE else View.GONE
            binding.icPlaystation.visibility = if(platforms.contains("playstation")) View.VISIBLE else View.GONE
            binding.icApple.visibility = if (platforms.contains("ios") || platforms.contains("mac")) View.VISIBLE else View.GONE
            binding.icAndroid.visibility = if(platforms.contains("android")) View.VISIBLE else View.GONE
            binding.icWindows.visibility = if (platforms.contains("pc")) View.VISIBLE else View.GONE
            binding.icLinux.visibility = if(platforms.contains("linux")) View.VISIBLE else View.GONE

            binding.clItem.setOnClickListener {
                game.id?.let { it1 -> listener.invoke(it1) }
            }

        }

    }
}