package com.ratan.maigen.ui.notifications

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ratan.maigen.databinding.FragmentNotificationsBinding
import com.ratan.maigen.view.activity.LoginActivity

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", Context. MODE_PRIVATE)
        val name = sharedPreferences.getString("name", "")
        val photoUrl = sharedPreferences.getString("photoUrl", "")

        if (name.isNullOrEmpty() || photoUrl.isNullOrEmpty()){
            redirectToLogin()
        } else  {
            updateUI(name,photoUrl)
        }

    }

    private fun updateUI(name: String, photoUrl: String) {
        binding.nameTextView.text = name
    }

    private fun redirectToLogin() {
        val intent = Intent(activity, NotificationsFragment::class.java)
        startActivity(intent)
        activity?.finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}