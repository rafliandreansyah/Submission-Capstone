package com.dicoding.submission_capstone.ui.favorite

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission_capstone.core.ui.GameAdapter
import com.dicoding.submission_capstone.databinding.ActivityFavoriteBinding
import com.dicoding.submission_capstone.ui.detail_game.DetailGameActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    private val favoriteViewModel: FavoriteViewModel by viewModels()

    @Inject
    lateinit var gameAdapter: GameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDataFavorite()
    }

    private fun getDataFavorite() {
        favoriteViewModel.getListGameFavorite().observe(this@FavoriteActivity) { listGameFavorite ->
            binding.loading.isVisible = false
            if (listGameFavorite.isNotEmpty()) {
                with(binding) {

                    rvGames.layoutManager = LinearLayoutManager(this@FavoriteActivity)
                    rvGames.adapter = gameAdapter
                    gameAdapter.setData(listGameFavorite)
                    gameAdapter.notifyDataSetChanged()

                    gameAdapter.setOnItemClickListener {
                        val intent = Intent(this@FavoriteActivity, DetailGameActivity::class.java)
                        intent.putExtra(DetailGameActivity.GAME_ID, it)
                        startActivity(intent)
                    }

                    with(binding){
                        btnBack.setOnClickListener {
                            finish()
                        }

                    }

                }
            } else {
                binding.tvEmpty.isVisible = true
                binding.rvGames.isVisible = false
            }
        }
    }
}