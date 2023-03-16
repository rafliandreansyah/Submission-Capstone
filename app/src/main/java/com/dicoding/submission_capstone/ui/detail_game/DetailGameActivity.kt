package com.dicoding.submission_capstone.ui.detail_game

import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.TextViewCompat
import com.bumptech.glide.Glide
import com.dicoding.submission_capstone.R
import com.dicoding.submission_capstone.core.data.source.Resource
import com.dicoding.submission_capstone.core.domain.model.DetailGame
import com.dicoding.submission_capstone.databinding.ActivityDetailGameBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailGameActivity : AppCompatActivity() {

    companion object {
        const val GAME_ID = "GAME_ID"
    }

    private lateinit var binding: ActivityDetailGameBinding

    private val detailGameViewModel: DetailGameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailGameViewModel.idGame = intent.getLongExtra(GAME_ID, 0L)
        if (detailGameViewModel.idGame == 0L) {
            finish()
            Toast.makeText(this, "Data not found", Toast.LENGTH_SHORT).show()
            return
        }

        getDataDetailGame(detailGameViewModel.idGame)

        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.btnSaveFavorite.isVisible = false
    }

    private fun getDataDetailGame(idGame: Long) {
        detailGameViewModel.getDetailGame(idGame).observe(this@DetailGameActivity) { detailGame ->
            when(detailGame) {
                is Resource.Success -> {
                    isLoading(false)
                    if (detailGame.data != null) {
                        setDataToView(detailGame.data)
                    } else {
                        Toast.makeText(this, "Data not found", Toast.LENGTH_SHORT).show()
                    }

                }
                is Resource.Error -> {
                    isLoading(false)
                    Toast.makeText(this@DetailGameActivity, detailGame.message, Toast.LENGTH_SHORT).show()

                }
                is Resource.Loading -> {
                    isLoading(true)
                }
            }

        }
    }

    private fun setDataToView(detailGameData: DetailGame){
        with(binding) {
            btnSaveFavorite.isVisible = true

            btnBack.setOnClickListener {
                finish()
            }

            statusFavorite(detailGameData.isFavorite)
            btnSaveFavorite.setOnClickListener {
                val data = detailGameData.copy(
                    isFavorite = !detailGameData.isFavorite
                )
                detailGameViewModel.saveToFavorite(data)
                statusFavorite(data.isFavorite)
            }

            Glide.with(this@DetailGameActivity)
                .load(detailGameData.backgroundImage)
                .centerCrop()
                .into(imgBgGames)

            tvTitleGame.text = detailGameData.name
            tvDescriptionGame.text = detailGameData.description
            val platforms = flexPlatform
            detailGameData.platForms?.forEachIndexed { index, platform ->
                val text = TextView(this@DetailGameActivity)
                text.text = if (index + 1 == detailGameData.platForms?.size) platform else "$platform, "
                TextViewCompat.setTextAppearance(text, R.style.TextRegular_12SP)
                text.paintFlags = Paint.UNDERLINE_TEXT_FLAG
                platforms.addView(text)
            }

            val genres = flexGenre
            detailGameData.genre?.forEachIndexed { index, genre ->
                val text = TextView(this@DetailGameActivity)
                text.text = if (index + 1 == detailGameData.genre?.size) genre else "$genre, "
                text.setTextColor(ContextCompat.getColor(this@DetailGameActivity, R.color.white))
                TextViewCompat.setTextAppearance(text, R.style.TextRegular_12SP)
                text.paintFlags = Paint.UNDERLINE_TEXT_FLAG
                genres.addView(text)
            }

            val developers = flexDeveloper
            detailGameData.developers?.forEachIndexed { index, developer ->
                val text = TextView(this@DetailGameActivity)
                text.text = if (index + 1 == detailGameData.developers?.size) developer else "$developer, "
                TextViewCompat.setTextAppearance(text, R.style.TextRegular_12SP)
                text.paintFlags = Paint.UNDERLINE_TEXT_FLAG
                developers.addView(text)
            }
        }
    }

    private fun isLoading(isL: Boolean) {
        with(binding) {
            if (isL) {
                loading.isVisible = true
                scrollView.isVisible = false
            } else {
                loading.isVisible = false
                scrollView.isVisible = true
            }
        }
    }

    private fun statusFavorite(isFavorite: Boolean) {
        if (isFavorite){
            binding.btnSaveFavorite.text = resources.getText(R.string.remove_to_favorite)
            binding.btnSaveFavorite.setBackgroundColor(ContextCompat.getColor(this@DetailGameActivity, R.color.colorRed))
        } else {
            binding.btnSaveFavorite.text = resources.getText(R.string.save_to_favorite)
            binding.btnSaveFavorite.setBackgroundColor(ContextCompat.getColor(this@DetailGameActivity, R.color.colorTeal))
        }
    }
}