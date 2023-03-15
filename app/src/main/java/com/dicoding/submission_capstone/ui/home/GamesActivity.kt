package com.dicoding.submission_capstone.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission_capstone.core.data.source.Resource
import com.dicoding.submission_capstone.core.ui.GameAdapter
import com.dicoding.submission_capstone.databinding.ActivityGamesBinding
import com.dicoding.submission_capstone.ui.detail_game.DetailGameActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GamesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGamesBinding

    private val gameViewModel: GameViewModel by viewModels()

    @Inject lateinit var gameAdapter: GameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGamesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDataGames()
    }

    private fun getDataGames() {
        gameViewModel.dataListGame.observe(this) { dataGames ->
            with(binding) {
                rvGames.layoutManager = LinearLayoutManager(this@GamesActivity)
                rvGames.adapter = gameAdapter
                gameAdapter.setOnItemClickListener {
                    val intent = Intent(this@GamesActivity, DetailGameActivity::class.java)
                    intent.putExtra(DetailGameActivity.GAME_ID, it)
                    startActivity(intent)
                }

                when(dataGames) {
                    is Resource.Success -> {
                        isLoading(false)
                        gameAdapter.setData(dataGames.data ?: ArrayList())
                    }
                    is Resource.Error -> {
                        isLoading(false)
                        Toast.makeText(this@GamesActivity, dataGames.message, Toast.LENGTH_SHORT).show()
                        Log.e(GamesActivity::class.java.simpleName, "Error: ${dataGames.message}")
                    }
                    is Resource.Loading -> {
                        isLoading(true)
                    }
                }

            }

        }
    }

    private fun isLoading(isL: Boolean) {
        with(binding) {
            if (isL){
                loading.isVisible = true
                rvGames.isVisible = false
            } else {
                loading.isVisible = false
                rvGames.isVisible = true
            }
        }
    }
}