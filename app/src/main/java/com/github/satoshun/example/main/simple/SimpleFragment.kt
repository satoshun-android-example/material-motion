package com.github.satoshun.example.main.simple

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.github.satoshun.example.R
import com.github.satoshun.example.databinding.SimpleDetailFragBinding
import com.github.satoshun.example.databinding.SimpleFragBinding
import com.github.satoshun.example.databinding.SimpleListFragBinding
import com.google.android.material.transition.MaterialContainerTransform

class SimpleFragment : Fragment(R.layout.simple_frag) {
  private lateinit var binding: SimpleFragBinding

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding = SimpleFragBinding.bind(view)

    val fragment = SimpleListFragment()
//    val fadeThrough = MaterialFadeThrough.create(requireContext())
//    fragment.enterTransition = fadeThrough

    childFragmentManager
      .beginTransaction()
      .replace(R.id.fragment_container, fragment)
      .commit()
  }
}

class SimpleListFragment : Fragment(R.layout.simple_list_frag) {
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val binding = SimpleListFragBinding.bind(view)

    val images = listOf(
      "https://pbs.twimg.com/profile_images/1227058593247064064/ssXXDIjO_400x400.jpg",
      "https://pbs.twimg.com/profile_images/1227058593247064064/ssXXDIjO_400x400.jpg",
      "https://pbs.twimg.com/profile_images/1227058593247064064/ssXXDIjO_400x400.jpg",
      "https://pbs.twimg.com/profile_images/1227058593247064064/ssXXDIjO_400x400.jpg",
      "https://pbs.twimg.com/profile_images/1227058593247064064/ssXXDIjO_400x400.jpg"
    )
    binding.recycler.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
      override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        object : RecyclerView.ViewHolder(
          LayoutInflater
            .from(parent.context)
            .inflate(R.layout.simple_item, parent, false)
        ) {}

      override fun getItemCount(): Int = images.size

      override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val imageView: ImageView = holder.itemView.findViewById(R.id.image)
        imageView.load(images[position])

        val sharedView = holder.itemView

        // TODO unique id
        ViewCompat.setTransitionName(sharedView, images[position])

        holder.itemView.setOnClickListener {
          parentFragmentManager
            .beginTransaction()
            .addSharedElement(sharedView, ViewCompat.getTransitionName(sharedView)!!)
            .replace(
              R.id.fragment_container,
              SimpleDetailFragment().apply { url = images[position] })
            .commit()
        }
      }
    }
    binding.recycler.layoutManager = LinearLayoutManager(requireContext())

//    val sharedAxis = MaterialSharedAxis.create(requireContext(), MaterialSharedAxis.Y, true)
//    sharedAxis.addTarget(RecyclerView::class.java)
//    TransitionManager.beginDelayedTransition(binding.root as ViewGroup, sharedAxis)
  }
}

class SimpleDetailFragment : Fragment(R.layout.simple_detail_frag) {
  lateinit var url: String

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val transform = MaterialContainerTransform(requireContext())
    sharedElementEnterTransition = transform
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val binding = SimpleDetailFragBinding.bind(view)

    // TODO unique id
    ViewCompat.setTransitionName(binding.container, url)

    binding.image.load(url)
  }
}
