package com.example.xardcalamityfiles.ui.create

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import coil.load
import coil.transform.CircleCropTransformation
import com.example.xardcalamityfiles.App
import com.example.xardcalamityfiles.R
import com.example.xardcalamityfiles.content.viewmodel.CreateUiState
import com.example.xardcalamityfiles.content.viewmodel.CreateViewModel
import com.example.xardcalamityfiles.content.viewmodel.ViewModelFactory
import com.example.xardcalamityfiles.data.model.Ability
import com.example.xardcalamityfiles.data.model.Character
import com.example.xardcalamityfiles.databinding.FragmentCharacterCreateBinding
import com.example.xardcalamityfiles.utils.UriUtils
import com.google.android.material.textfield.TextInputEditText

class CharacterCreateFragment : Fragment() {

    private var _binding: FragmentCharacterCreateBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CreateViewModel by viewModels {
        ViewModelFactory((requireActivity().application as App).repository)
    }

    private val args: CharacterCreateFragmentArgs by navArgs()

    private var selectedProfileUri: Uri? = null

    // Track dynamic views to extract data later
    private val dynamicAbilitiesData = mutableListOf<DynamicAbilityEntry>()
    private val fixedAbilitiesData = mutableMapOf<String, DynamicAbilityEntry>()

    private var currentImageTarget: ImageView? = null
    private var currentUriCallback: ((Uri) -> Unit)? = null

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            UriUtils.takePersistableUriPermission(requireContext(), it)
            currentUriCallback?.invoke(it)
            currentImageTarget?.load(it) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarCreate.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        setupDropdowns()
        setupFixedAbilities()
        setupListeners()

        val characterId = args.characterId
        if (characterId != -1L) {
            binding.toolbarCreate.title = "Edit Character"
            viewModel.loadCharacter(characterId)
            observeViewModel()
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { state ->
                    if (state is CreateUiState.Success) {
                        populateUi(state.characterWithAbilities)
                    }
                }
            }
        }
    }

    private fun populateUi(data: com.example.xardcalamityfiles.data.model.CharacterWithAbilities) {
        val char = data.character
        binding.etName.setText(char.name)
        binding.spinnerClass.setText(char.characterClass, false)
        binding.spinnerSubclass.setText(char.subclass, false)

        if (!char.profilePictureUri.isNullOrEmpty()) {
            selectedProfileUri = Uri.parse(char.profilePictureUri)
            binding.ivCreateProfile.load(selectedProfileUri) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
        }

        binding.llDynamicAbilities.removeAllViews()
        dynamicAbilitiesData.clear()

        data.abilities.forEach { ability ->
            when (ability.type) {
                "BASIC", "ABILITY_1", "ABILITY_2", "ABILITY_3", "SUPREME" -> {
                    val entry = fixedAbilitiesData[ability.type]
                    if (entry != null) {
                        entry.etName.setText(ability.name)
                        entry.etDesc.setText(ability.description)
                        if (!ability.iconUri.isNullOrEmpty()) {
                            entry.iconUri = Uri.parse(ability.iconUri)
                            entry.ivIcon?.load(entry.iconUri) {
                                crossfade(true)
                                transformations(CircleCropTransformation())
                            }
                        }
                    }
                }
                "PASSIVE", "EFFECT" -> {
                    addDynamicAbilityWithData(ability)
                }
            }
        }
    }

    private fun setupDropdowns() {
        val classes = arrayOf("Attacker", "Controller", "Support", "Defender")
        val adapterClass = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, classes)
        binding.spinnerClass.setAdapter(adapterClass)

        val subclasses = arrayOf("Offensive", "Disruptor", "Catalyst", "Protector")
        val adapterSub = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, subclasses)
        binding.spinnerSubclass.setAdapter(adapterSub)
    }

    private fun setupListeners() {
        val clickImage = View.OnClickListener {
            currentImageTarget = binding.ivCreateProfile
            currentUriCallback = { uri -> selectedProfileUri = uri }
            pickImage.launch("image/*")
        }
        binding.ivCreateProfile.setOnClickListener(clickImage)
        binding.btnPickImage.setOnClickListener(clickImage)

        binding.btnAddPassive.setOnClickListener {
            addDynamicAbility("PASSIVE")
        }

        binding.btnAddEffect.setOnClickListener {
            addDynamicAbility("EFFECT")
        }

        binding.btnSave.setOnClickListener {
            saveCharacter()
        }
    }

    private fun setupFixedAbilities() {
        addFixedAbility(binding.llBasicAttack, "BASIC", "Basic Attack")
        addFixedAbility(binding.llAbility1, "ABILITY_1", "Ability 1")
        addFixedAbility(binding.llAbility2, "ABILITY_2", "Ability 2")
        addFixedAbility(binding.llAbility3, "ABILITY_3", "Ability 3")
        addFixedAbility(binding.llSupreme, "SUPREME", "Supreme Ability")
    }

    private fun addFixedAbility(container: LinearLayout, type: String, label: String) {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.partial_ability_input, container, true)
        val tvLabel = view.findViewById<TextView>(R.id.tvAbilityLabel)
        val ivIcon = view.findViewById<ImageView>(R.id.ivIconPicker)
        val etName = view.findViewById<TextInputEditText>(R.id.etAbilityName)
        val etDesc = view.findViewById<TextInputEditText>(R.id.etAbilityDesc)

        tvLabel.text = label
        
        val entry = DynamicAbilityEntry(type, etName, etDesc, null, ivIcon)
        fixedAbilitiesData[type] = entry

        ivIcon.setOnClickListener {
            currentImageTarget = ivIcon
            currentUriCallback = { uri -> entry.iconUri = uri }
            pickImage.launch("image/*")
        }
    }

    private fun addDynamicAbility(type: String) {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.partial_ability_input, binding.llDynamicAbilities, false)
        val tvLabel = view.findViewById<TextView>(R.id.tvAbilityLabel)
        val ivIcon = view.findViewById<ImageView>(R.id.ivIconPicker)
        val btnRemove = view.findViewById<ImageButton>(R.id.btnRemove)
        val etName = view.findViewById<TextInputEditText>(R.id.etAbilityName)
        val etDesc = view.findViewById<TextInputEditText>(R.id.etAbilityDesc)

        tvLabel.text = if (type == "PASSIVE") "Passive" else "Effect"
        btnRemove.visibility = View.VISIBLE

        val entry = DynamicAbilityEntry(type, etName, etDesc, null, ivIcon)
        dynamicAbilitiesData.add(entry)

        ivIcon.setOnClickListener {
            currentImageTarget = ivIcon
            currentUriCallback = { uri -> entry.iconUri = uri }
            pickImage.launch("image/*")
        }

        btnRemove.setOnClickListener {
            binding.llDynamicAbilities.removeView(view)
            dynamicAbilitiesData.remove(entry)
        }

        binding.llDynamicAbilities.addView(view)
    }

    private fun addDynamicAbilityWithData(ability: com.example.xardcalamityfiles.data.model.Ability) {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.partial_ability_input, binding.llDynamicAbilities, false)
        val tvLabel = view.findViewById<TextView>(R.id.tvAbilityLabel)
        val ivIcon = view.findViewById<ImageView>(R.id.ivIconPicker)
        val btnRemove = view.findViewById<ImageButton>(R.id.btnRemove)
        val etName = view.findViewById<TextInputEditText>(R.id.etAbilityName)
        val etDesc = view.findViewById<TextInputEditText>(R.id.etAbilityDesc)

        tvLabel.text = if (ability.type == "PASSIVE") "Passive" else "Effect"
        btnRemove.visibility = View.VISIBLE

        etName.setText(ability.name)
        etDesc.setText(ability.description)
        
        var iconUri: Uri? = null
        if (!ability.iconUri.isNullOrEmpty()) {
            iconUri = Uri.parse(ability.iconUri)
            ivIcon.load(iconUri) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
        }

        val entry = DynamicAbilityEntry(ability.type, etName, etDesc, iconUri, ivIcon)
        dynamicAbilitiesData.add(entry)

        ivIcon.setOnClickListener {
            currentImageTarget = ivIcon
            currentUriCallback = { uri -> entry.iconUri = uri }
            pickImage.launch("image/*")
        }

        btnRemove.setOnClickListener {
            binding.llDynamicAbilities.removeView(view)
            dynamicAbilitiesData.remove(entry)
        }

        binding.llDynamicAbilities.addView(view)
    }

    private fun saveCharacter() {
        val name = binding.etName.text.toString()
        val charClass = binding.spinnerClass.text.toString()
        val subclass = binding.spinnerSubclass.text.toString()

        if (name.isBlank() || charClass.isBlank() || subclass.isBlank()) {
            Toast.makeText(requireContext(), "Name, Class, and Subclass are required", Toast.LENGTH_SHORT).show()
            return
        }

        val character = Character(
            id = if (args.characterId != -1L) args.characterId else 0L,
            name = name,
            characterClass = charClass,
            subclass = subclass,
            profilePictureUri = selectedProfileUri?.toString()
        )

        val abilitiesToSave = mutableListOf<Ability>()

        // Add fixed abilities
        fixedAbilitiesData.forEach { (type, entry) ->
            abilitiesToSave.add(
                Ability(
                    characterId = 0, // Ignored by Room insertion for new character
                    type = type,
                    name = entry.etName.text.toString().ifBlank { type.replace("_", " ") },
                    description = entry.etDesc.text.toString(),
                    iconUri = entry.iconUri?.toString()
                )
            )
        }

        // Add dynamic abilities
        dynamicAbilitiesData.forEach { entry ->
            val typedName = entry.etName.text.toString()
            if (typedName.isNotBlank() || entry.etDesc.text.toString().isNotBlank()) {
                abilitiesToSave.add(
                    Ability(
                        characterId = 0,
                        type = entry.type,
                        name = typedName.ifBlank { "Unnamed ${entry.type}" },
                        description = entry.etDesc.text.toString(),
                        iconUri = entry.iconUri?.toString()
                    )
                )
            }
        }

        viewModel.saveCharacter(character, abilitiesToSave) {
            Toast.makeText(requireContext(), "Character Saved", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private data class DynamicAbilityEntry(
        val type: String,
        val etName: TextInputEditText,
        val etDesc: TextInputEditText,
        var iconUri: Uri?,
        val ivIcon: ImageView? = null
    )
}
