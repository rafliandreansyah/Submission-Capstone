package com.dicoding.submission_capstone.favorite

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission_capstone.core.ui.GameAdapter
import com.dicoding.submission_capstone.databinding.ActivityFavoriteBinding
import com.dicoding.submission_capstone.di.FavoriteModuleDependencies
import com.dicoding.submission_capstone.ui.detail_game.DetailGameActivity
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject
class FavoriteActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory

    private val favoriteViewModel: FavoriteViewModel by viewModels{
        factory
    }

    private lateinit var gameAdapter: GameAdapter

    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerFavoriteComponent.builder()
            .context(this)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    applicationContext,
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gameAdapter = GameAdapter(this)

        binding.btnBack.setOnClickListener {
            finish()
        }

        getDataFavorite()
    }

    private fun getDataFavorite() {
        favoriteViewModel.getListGameFavorite().observe(this@FavoriteActivity) { listGameFavorite ->
            binding.loading.isVisible = false
            if (listGameFavorite.isNotEmpty()) {
                with(binding) {

                    rvGames.layoutManager = LinearLayoutManager(this@FavoriteActivity)
                    rvGames.adapter = gameAdapter
                    gameAdapter.submitList(listGameFavorite)

                    gameAdapter.setOnItemClickListener {
                        val intent = Intent(this@FavoriteActivity, DetailGameActivity::class.java)
                        intent.putExtra(DetailGameActivity.GAME_ID, it)
                        intent.putExtra(DetailGameActivity.IS_FAVORITE, true)
                        startActivity(intent)
                    }

                }
            } else {
                binding.tvEmpty.isVisible = true
                binding.rvGames.isVisible = false
            }
        }
    }
}