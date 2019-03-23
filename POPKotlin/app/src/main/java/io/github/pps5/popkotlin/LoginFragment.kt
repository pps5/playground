package io.github.pps5.popkotlin

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.pps5.popkotlin.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    companion object {
        private val VALID_ID_PASSWORD = "user1" to "Password1111"
    }

    private lateinit var binding: FragmentLoginBinding

    private val onLoginClickListener = View.OnClickListener {
        val (id, password) = binding.userId.text.toString() to binding.password.text.toString()
        if (id == VALID_ID_PASSWORD.first && password == VALID_ID_PASSWORD.second) {
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container, LoginCompleteFragment.newInstance(id))
                ?.commit()
        } else {
            binding.passwordLayout.error = getString(R.string.invalid_id_or_password)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.loginClickListener = onLoginClickListener
        return binding.root
    }

}