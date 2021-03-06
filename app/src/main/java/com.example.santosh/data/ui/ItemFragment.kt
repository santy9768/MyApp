package com.example.santosh.data.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.santosh.R
import com.example.santosh.data.ui.viewmodel.ItemViewModel
import com.example.santosh.databinding.ItemFragmentBinding
import com.example.santosh.utils.Resource
import com.example.santosh.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import androidx.fragment.app.viewModels

@AndroidEntryPoint
class ItemFragment : Fragment(),ItemAdapter.itemsItemListener{

  private var binding: ItemFragmentBinding by autoCleared()
  private val viewModel: ItemViewModel by viewModels()
  private lateinit var adapter: ItemAdapter

  override fun onCreateView(
          inflater: LayoutInflater, container: ViewGroup?,
          savedInstanceState: Bundle?
  ): View? {
    binding = ItemFragmentBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupRecyclerView()
    setupObservers()
  }

  private fun setupRecyclerView() {
    adapter = ItemAdapter(this)
    binding.itemssRv.layoutManager = LinearLayoutManager(requireContext())
    binding.itemssRv.adapter = adapter
  }

  private fun setupObservers() {
    viewModel.items.observe(viewLifecycleOwner, Observer {
      when (it.status) {
        Resource.Status.SUCCESS -> {
          binding.progressBar.visibility = View.GONE
          if (!it.data.isNullOrEmpty()) adapter.setItems(ArrayList(it.data))
        }
        Resource.Status.ERROR ->
          Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

        Resource.Status.LOADING ->
          binding.progressBar.visibility = View.VISIBLE
      }
    })
  }

  override fun onClickeditems(itemsId: Int) {
    findNavController().navigate(
            R.id.action_itemssFragment_to_itemsDetailFragment,
            bundleOf("id" to itemsId)
    )
  }
}