package com.ratan.maigen.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ratan.maigen.R
import com.ratan.maigen.additional.Destination
import com.ratan.maigen.additional.ListDestinationAdapter
import com.ratan.maigen.databinding.FragmentHomeBinding
import java.lang.reflect.Member

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private var rvDestination: RecyclerView? = null
    private val list = ArrayList<Destination>()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root

        val view = inflater.inflate(R.layout.activity_main, container, false)

        rvDestination = view.findViewById(R.id.rv_destination)
        rvDestination?.setHasFixedSize(true)

        list.addAll(getListDestinations())
        showRecyclerList()

        return view
    }

    private fun getListDestinations(): ArrayList<Destination> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.getStringArray(R.array.data_photo)
        val listDestination = ArrayList<Destination>()
        for (i in dataName.indices) {
            val destination = Destination(dataName[i], dataDescription[i], dataPhoto[i])
            listDestination.add(destination)
        }
        return listDestination
    }

    private fun showRecyclerList() {
        rvDestination?.layoutManager = LinearLayoutManager(requireContext())
        val listDestinationAdapter = ListDestinationAdapter(list)
        rvDestination?.adapter = listDestinationAdapter

        listDestinationAdapter.setOnItemClickCallback(object : ListDestinationAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Destination) {
                showSelectedDestination(data)
            }
        })
    }

    private fun showSelectedDestination(destination: Destination) {
        Toast.makeText(requireContext(), "You Picked " + destination.name, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}