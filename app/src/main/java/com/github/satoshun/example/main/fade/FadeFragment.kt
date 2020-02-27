package com.github.satoshun.example.main.fade

import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.github.satoshun.example.R
import com.github.satoshun.example.databinding.FadeFragBinding
import com.google.android.material.transition.MaterialFade
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FadeFragment : Fragment(R.layout.fade_frag) {
  private lateinit var binding: FadeFragBinding

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding = FadeFragBinding.bind(view)

    var entering = false
    viewLifecycleOwner.lifecycleScope.launch {
      while (true) {
        delay(2000)
        val fade = MaterialFade.create(requireContext(), entering)
        TransitionManager.beginDelayedTransition(binding.root as ViewGroup, fade)
        binding.fadeFab.isVisible = entering
        entering = !entering
      }
    }
  }
}
