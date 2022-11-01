package com.example.daggernewsapplication.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.daggernewsapplication.R
import com.example.daggernewsapplication.databinding.FragmentMainBinding
import com.example.daggernewsapplication.di.MyApplication
import com.example.daggernewsapplication.ui.adapter.NewsAdapter
import com.example.daggernewsapplication.ui.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    private lateinit var adapter : NewsAdapter
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: MainViewModel by activityViewModels() {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity().application as MyApplication).appComponent?.inject(this)
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = NewsAdapter()

        adapter.itemClickListener = {
            viewModel.updateArticles(it)
            findNavController().navigate(R.id.action_mainFragment_to_detailsFragment)
        }
        binding.header.setText(resources.getText(R.string.app_name))
        binding.btnClear.setOnClickListener(View.OnClickListener { binding.edtSearch.setText("") })

        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                viewModel.updateText(binding.edtSearch.getText().toString())
                viewModel.updatePage(5)
                viewModel.getNews(viewModel.queryText, viewModel.page)
            }
        })
        initObserver()
    }

    private fun initObserver(){
        viewModel.articles.observe(viewLifecycleOwner) {
            adapter.setItems(it)
            binding.recyclerView!!.adapter = adapter
            binding.progressBar.visibility = View.GONE
        }
    }

    }
