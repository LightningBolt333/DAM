package com.example.woofwoof

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.woofwoof.core.network.ApiClient
import com.example.woofwoof.core.repository.DogRepository
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
            viewModel.loadItems(breed)
        }
    }

    private fun setupRecyclerView() {
        adapter = DogAdapter()
        binding.recyclerView.adapter = adapter
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadItems()
        }
        binding.btnRetry.setOnClickListener {
            viewModel.loadItems()
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    // Handle loading
                    if (state.isLoading) {
                        if (!binding.swipeRefreshLayout.isRefreshing) {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                    } else {
                        binding.progressBar.visibility = View.GONE
                        binding.swipeRefreshLayout.isRefreshing = false
                    }

                    // Handle list
                    adapter.submitList(state.dogs)

                    // Handle error
                    if (state.errorMessage != null) {
                        binding.tvError.visibility = View.VISIBLE
                        binding.btnRetry.visibility = View.VISIBLE
                        binding.tvError.text = state.errorMessage
                    } else {
                        binding.tvError.visibility = View.GONE
                        binding.btnRetry.visibility = View.GONE
                    }
                }
            }
        }
    }
}
