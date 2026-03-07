package com.example.xardcalamityfiles.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.CircleCropTransformation
import com.example.xardcalamityfiles.App
import com.example.xardcalamityfiles.content.viewmodel.DetailsUiState
import com.example.xardcalamityfiles.content.viewmodel.DetailsViewModel
import com.example.xardcalamityfiles.content.viewmodel.ViewModelFactory
import com.example.xardcalamityfiles.databinding.FragmentCharacterDetailsBinding
import io.noties.markwon.Markwon
import io.noties.markwon.ext.strikethrough.StrikethroughPlugin
import io.noties.markwon.ext.tables.TablePlugin
import io.noties.markwon.image.coil.CoilImagesPlugin
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharacterDetailsFragment : Fragment() {

    private var _binding: FragmentCharacterDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailsViewModel by viewModels {
        ViewModelFactory((requireActivity().application as App).repository)
    }

    private val args: CharacterDetailsFragmentArgs by navArgs()
    private lateinit var adapter: AbilityAdapter
    private lateinit var markwon: Markwon

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.toolbar.inflateMenu(com.example.xardcalamityfiles.R.menu.menu_details)
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                com.example.xardcalamityfiles.R.id.action_edit -> {
                    val bundle = Bundle().apply {
                        putLong("characterId", args.characterId)
                    }
                    findNavController().navigate(com.example.xardcalamityfiles.R.id.action_characterDetailsFragment_to_characterCreateFragment, bundle)
                    true
                }
                else -> false
            }
        }

        markwon = Markwon.builder(requireContext())
            .usePlugin(StrikethroughPlugin.create())
            .usePlugin(TablePlugin.create(requireContext()))
            .usePlugin(CoilImagesPlugin.create(requireContext()))
            .build()
            
        adapter = AbilityAdapter(markwon)
        binding.rvAbilities.adapter = adapter
        binding.rvAbilities.layoutManager = LinearLayoutManager(requireContext())

        viewModel.loadCharacter(args.characterId)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { state ->
                    when (state) {
                        is DetailsUiState.Loading -> {
                            // Optionally show loading spinner
                        }
                        is DetailsUiState.Success -> {
                            val data = state.characterWithAbilities
                            binding.tvNameDetail.text = data.character.name
                            binding.tvClassDetail.text = "${data.character.characterClass} • ${data.character.subclass}"
                            
                            binding.ivProfileDetail.load(data.character.profilePictureUri) {
                                crossfade(true)
                                transformations(CircleCropTransformation())
                            }
                            // Abilities usually have 1 basic, 3 abilities, 1 supreme, passives, effects
                            // We can sort them here or assume order is fine
                            adapter.submitList(data.abilities)
                        }
                        is DetailsUiState.NotFound -> {
                            Toast.makeText(requireContext(), "Character not found", Toast.LENGTH_SHORT).show()
                            findNavController().navigateUp()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
