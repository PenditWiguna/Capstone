package com.ratan.maigen.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ratan.maigen.data.response.ExploreResponse
import com.ratan.maigen.data.response.ExploreResult
import com.ratan.maigen.databinding.FragmentExploreBinding
import com.ratan.maigen.view.adapter.ExploreAdapter
import kotlinx.coroutines.launch

class ExploreFragment : Fragment() {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!
    private lateinit var exploreViewModel: ExploreViewModel
    private lateinit var exploreAdapter: ExploreAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        exploreViewModel = ViewModelProvider(this).get(ExploreViewModel::class.java)
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupRecyclerView()
        setupSearchView()

        exploreViewModel.exploreData.observe(viewLifecycleOwner) { data ->
            exploreAdapter.submitList(data)
        }

        return root
    }

    private fun setupRecyclerView() {
        exploreAdapter = ExploreAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = exploreAdapter
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val exploreResult = listOf(
            ExploreResult(
                Place_Id = "1",
                Place_Name = "Tempat Wisata 1",
                Description = "Deskripsi tempat wisata 1",
                Weekend_Holiday_Price = "10000",
                Weekday_Price = "8000",
                Category = "Alam",
                City = "Kota A",
                Rating = "4.5",
                Alamat = "Alamat 1",
                Coordinate = "-8.3405, 115.092",
                Lat = "-8.3405",
                Long = "115.092",
                Gambar = "url_gambar_1"
            )
        )

        exploreAdapter = ExploreAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = exploreAdapter
        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener,
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    lifecycleScope.launch {
                      //  exploreViewModel.searchDestination(it)
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    exploreViewModel.searchDestination(it)
                }
                // Optional: Implement if you want to filter while typing
                return false
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}