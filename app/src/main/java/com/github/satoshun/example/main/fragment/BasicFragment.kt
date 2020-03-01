package com.github.satoshun.example.main.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import coil.api.load
import com.github.satoshun.example.R
import com.github.satoshun.example.databinding.Basic1FragBinding
import com.github.satoshun.example.databinding.Basic2FragBinding
import com.github.satoshun.example.databinding.BasicFragBinding
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialContainerTransform

class BasicFragment : Fragment(R.layout.basic_frag) {
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val binding = BasicFragBinding.bind(view)

    val fragment = Basic1Fragment()
    childFragmentManager
      .beginTransaction()
      .replace(R.id.fragment_container, fragment)
      .commit()
  }
}

class Basic1Fragment : Fragment(R.layout.basic1_frag) {
  private val TAG = "TAG"

  private val holdTransition = Hold()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val binding = Basic1FragBinding.bind(view)

    binding.fab.setOnClickListener {
      val fragment = Basic2Fragment()
      val sharedElement = it
      configureTransitions(fragment)
      parentFragmentManager
        .beginTransaction()
        .addSharedElement(sharedElement, "shared_element_end_root")
        .replace(
          R.id.fragment_container,
          fragment,
          TAG
        )
        .addToBackStack("ContainerTransformFragment::end")
        .commit()
    }
  }

  private fun configureTransitions(fragment: Fragment) {
    val enterContainerTransform = buildContainerTransform()
    fragment.sharedElementEnterTransition = enterContainerTransform
    holdTransition.duration = enterContainerTransform.duration
    val returnContainerTransform = buildContainerTransform()
    fragment.sharedElementReturnTransition = returnContainerTransform
  }

  private fun buildContainerTransform(): MaterialContainerTransform {
    return MaterialContainerTransform(requireContext()).apply {
      pathMotion = MaterialArcMotion()
      isDrawDebugEnabled = true
    }
  }
}

class Basic2Fragment : Fragment(R.layout.basic2_frag) {
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val binding = Basic2FragBinding.bind(view)

    binding.image.load("https://pbs.twimg.com/profile_images/1227058593247064064/ssXXDIjO_400x400.jpg")
  }
}
