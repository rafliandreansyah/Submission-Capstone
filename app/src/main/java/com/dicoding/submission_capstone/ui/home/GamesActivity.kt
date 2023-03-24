package com.dicoding.submission_capstone.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission_capstone.core.data.source.Resource
import com.dicoding.submission_capstone.core.ui.GameAdapter
import com.dicoding.submission_capstone.databinding.ActivityGamesBinding
import com.dicoding.submission_capstone.ui.detail_game.DetailGameActivity
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
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

        binding.btnMenuFavorite.setOnClickListener {
            try {
                installFavoriteModule()
            }catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@GamesActivity, "Module not found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun installFavoriteModule() {
        val splitInstallManager = SplitInstallManagerFactory.create(this)
        val favoriteModule = "favorite"
        if (splitInstallManager.installedModules.contains(favoriteModule)) {
            moveToFavorite()
        } else {
            val request = SplitInstallRequest.newBuilder()
                .addModule(favoriteModule)
                .build()

            splitInstallManager.startInstall(request)
                .addOnSuccessListener {
                    Toast.makeText(this, "Success installing Favorite", Toast.LENGTH_SHORT).show()
                    moveToFavorite()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error installing Favorite", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun moveToFavorite() {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("submission_capstone://favorite")))

    }

    override fun onResume() {
        super.onResume()
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
                        tvErrorMessage.isVisible = true
                        tvErrorMessage.text = dataGames.message
                        Toast.makeText(this@GamesActivity, dataGames.message, Toast.LENGTH_SHORT).show()
                        Log.e(GamesActivity::class.java.simpleName, "Error: ${dataGames.message}")
                    }
                    else -> {
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