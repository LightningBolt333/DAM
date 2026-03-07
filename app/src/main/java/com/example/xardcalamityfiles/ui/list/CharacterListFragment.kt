package com.example.xardcalamityfiles.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xardcalamityfiles.App
import com.example.xardcalamityfiles.R
import com.example.xardcalamityfiles.content.viewmodel.ListViewModel
import com.example.xardcalamityfiles.content.viewmodel.ViewModelFactory
import com.example.xardcalamityfiles.databinding.FragmentCharacterListBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharacterListFragment : Fragment() {

    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ListViewModel by viewModels {
        ViewModelFactory((requireActivity().application as App).repository)
    }

    private lateinit var adapter: CharacterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        binding.fabAddCharacter.setOnClickListener {
            // TODO: Navigate to create screen
            findNavController().navigate(R.id.action_characterListFragment_to_characterCreateFragment)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.allCharacters.collectLatest { characters ->
                    adapter.submitList(characters)
                    if (characters.isEmpty()) {
                        binding.emptyStateText.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.GONE
                    } else {
                        binding.emptyStateText.visibility = View.GONE
                        binding.recyclerView.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = CharacterAdapter { characterId ->
            // TODO: Navigate to details view
            val action = CharacterListFragmentDirections.actionCharacterListFragmentToCharacterDetailsFragment(characterId)
            findNavController().navigate(action)
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
