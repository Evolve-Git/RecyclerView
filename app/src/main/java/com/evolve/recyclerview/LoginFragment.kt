package com.evolve.recyclerview

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.evolve.recyclerview.data.BASE_URL
import com.evolve.recyclerview.data.models.DataViewModel
import com.evolve.recyclerview.databinding.FragmentLoginBinding
import com.evolve.recyclerview.utility.retrieveImage

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: DataViewModel by activityViewModels()

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_login,container,false)

        binding.webView.settings.javaScriptEnabled = true
        val REALM_PARAM = "Recyclerview"

        if (requireActivity().isNetworkConnected()) {
            viewModel.network = true

            val url = "https://steamcommunity.com/openid/login?" +
                    "openid.claimed_id=http://specs.openid.net/auth/2.0/identifier_select&" +
                    "openid.identity=http://specs.openid.net/auth/2.0/identifier_select&" +
                    "openid.mode=checkid_setup&" +
                    "openid.ns=http://specs.openid.net/auth/2.0&" +
                    "openid.realm=https://" + REALM_PARAM + "&" +
                    "openid.return_to=https://" + REALM_PARAM + "/signin/"
            binding.webView.loadUrl(url)
            binding.webView.webViewClient = object : WebViewClient() {
                override fun onPageStarted(
                    view: WebView, url: String,
                    favicon: Bitmap?
                ) {
                    val userUrl: Uri = Uri.parse(url)
                    if (userUrl.authority.equals(REALM_PARAM.lowercase())) {
                        // That means that authentication is finished and the url contains user's id.
                        binding.webView.stopLoading()

                        // Extracts user id.
                        val userAccountUrl: Uri = Uri.parse(userUrl.getQueryParameter("openid.identity"))

                        viewModel.userId = userAccountUrl.lastPathSegment!!

                        view.findNavController().navigate(R.id.action_loginFragment_to_loadingFragment)
                    }
                }
            }
        } else {
            noNetwork()
        }

        return binding.root
    }

    private fun noNetwork(){
        AlertDialog.Builder(requireContext()).setTitle("No Internet Connection!")
            .setMessage("Continue in offline mode?")
            .setPositiveButton(android.R.string.ok) { _, _ ->
                view?.findNavController()?.navigate(R.id.action_loginFragment_to_loadingFragment)}
            .setNegativeButton(android.R.string.cancel) { _, _ -> requireActivity().finish()}
            .setIcon(android.R.drawable.ic_dialog_alert).show()
    }

    override fun onStart() {
        super.onStart()
        if (viewModel.network) retrieveImage(android.R.drawable.ic_menu_report_image,
            binding.root, "$BASE_URL/favicon.ico",
            (requireActivity() as MainActivity).activity.icon)
        else (requireActivity() as MainActivity).supportActionBar?.title =
            resources.getString(R.string.app_name) + " in Offline mode"
    }
}