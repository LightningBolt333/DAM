package com.example.woofwoof

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.woofwoof.data.api.ApiClient
import com.example.woofwoof.data.repository.DogRepository
import com.example.woofwoof.databinding.ActivityMainBinding
import com.example.woofwoof.ui.DogAdapter
import com.example.woofwoof.ui.MainViewModel
import com.example.woofwoof.ui.MainViewModelFactory
import com.example.woofwoof.ui.UiState
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: DogAdapter
    
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(DogRepository(ApiClient.dogApiService))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupSwipeRefresh()
        setupSearch()
        observeViewModel()
    }

    private fun setupSearch() {
        binding.btnSearch.setOnClickListener {
            val breed = binding.etSearch.text.toString()
            viewModel.fetchDogs(breed)
        }
    }

    private fun setupRecyclerView() {
        adapter = DogAdapter()
        binding.recyclerView.adapter = adapter
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchDogs()
        }
        binding.btnRetry.setOnClickListener {
            viewModel.fetchDogs()
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is UiState.Loading -> {
                            if (!binding.swipeRefreshLayout.isRefreshing) {
                                binding.progressBar.visibility = View.VISIBLE
                            }
                            binding.tvError.visibility = View.GONE
                            binding.btnRetry.visibility = View.GONE
                        }
                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.tvError.visibility = View.GONE
                            binding.btnRetry.visibility = View.GONE
                            binding.swipeRefreshLayout.isRefreshing = false
                            adapter.submitList(state.dogs)
                        }
                        is UiState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.swipeRefreshLayout.isRefreshing = false
                            binding.tvError.visibility = View.VISIBLE
                            binding.btnRetry.visibility = View.VISIBLE
                            binding.tvError.text = state.message
                        }
                    }
                }
            }
        }
    }
}
