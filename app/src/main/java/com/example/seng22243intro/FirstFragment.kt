package com.example.seng22243intro

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.seng22243intro.api.UserAPIService
import com.example.seng22243intro.databinding.FragmentFirstBinding
import com.example.seng22243intro.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val userAPIService = UserAPIService.create()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonFirst.setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            val user = userAPIService.getUser(binding.editTextFirst.text.toString())
            user.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    val body = response.body()
                    body?.let {
                        Log.i("FirstFragment :", "Successful")

                        binding.textviewFirst.text = "User Details"
                        binding.textviewUserid.text = "User ID : " + it.id
                        binding.textviewName.text = "Name : " + it.name
                        binding.textviewUsername.text = "User Name : " + it.username
                        binding.textviewEmail.text = "Email : " + it.email
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.i("FirstFragment :", "Error")
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}