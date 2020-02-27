package com.github.satoshun.example.main.fadethrough

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.github.satoshun.example.R
import com.github.satoshun.example.databinding.FadeThroughFragBinding
import com.google.android.material.transition.MaterialFadeThrough

class FadeThroughFragment : Fragment(R.layout.fade_through_frag) {
  private lateinit var binding: FadeThroughFragBinding

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding = FadeThroughFragBinding.bind(view)

    binding.bottom.setOnNavigationItemSelectedListener {
      replaceFragment(it.itemId)
      true
    }

    replaceFragment(R.id.action1)
  }

  private fun replaceFragment(id: Int) {
    val fragment = when (id) {
      R.id.action1 -> FadeThrough1Fragment()
      else -> FadeThrough2Fragment()
    }

    val fadeThrough = MaterialFadeThrough.create(requireContext())
    fadeThrough.addTarget(R.id.fade1_root)
    fadeThrough.addTarget(R.id.fade2_root)

    fragment.enterTransition = fadeThrough

    childFragmentManager
      .beginTransaction()
      .replace(R.id.fragment_container, fragment)
      .commit()
  }
}

class FadeThrough1Fragment : Fragment(R.layout.fade_through1_frag) {
}

class FadeThrough2Fragment : Fragment(R.layout.fade_through2_frag) {
}
