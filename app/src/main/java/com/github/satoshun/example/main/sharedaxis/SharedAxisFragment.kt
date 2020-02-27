package com.github.satoshun.example.main.sharedaxis

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.github.satoshun.example.R
import com.github.satoshun.example.databinding.SharedAxisFragBinding
import com.github.satoshun.example.main.fadethrough.FadeThrough1Fragment
import com.github.satoshun.example.main.fadethrough.FadeThrough2Fragment
import com.google.android.material.transition.MaterialSharedAxis

class SharedAxisFragment : Fragment(R.layout.shared_axis_frag) {
  private lateinit var binding: SharedAxisFragBinding

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding = SharedAxisFragBinding.bind(view)

    binding.back.setOnClickListener {
      replaceFragment(it.id)
    }
    binding.next.setOnClickListener {
      replaceFragment(it.id)
    }

    replaceFragment(R.id.back)
  }

  private fun replaceFragment(id: Int) {
    val fragment = when (id) {
      R.id.next -> FadeThrough1Fragment()
      else -> FadeThrough2Fragment()
    }

    val sharedAxis = MaterialSharedAxis.create(
      requireContext(),
      MaterialSharedAxis.Y,
      true
    )

    fragment.enterTransition = sharedAxis

    childFragmentManager
      .beginTransaction()
      .replace(R.id.fragment_container, fragment)
      .commit()
  }
}
