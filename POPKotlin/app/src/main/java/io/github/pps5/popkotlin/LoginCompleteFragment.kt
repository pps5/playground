package io.github.pps5.popkotlin

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.pps5.popkotlin.databinding.FragmentLoginCompleteBinding

class LoginCompleteFragment : Fragment() {

    companion object {
        private const val ARGS_KEY_USER_ID = "user_id"

        fun newInstance(userId: String) = LoginCompleteFragment().also {
            it.arguments = Bundle().apply { putString(ARGS_KEY_USER_ID, userId) }
        }
    }

    private lateinit var binding: FragmentLoginCompleteBinding

    private val onLogoutClickListener = View.OnClickListener {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.container, LoginFragment())
            ?.commit()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login_complete, container, false)
        binding.userId = arguments?.getString(ARGS_KEY_USER_ID)
        binding.onLogoutClickListener = onLogoutClickListener
        return binding.root
    }
}