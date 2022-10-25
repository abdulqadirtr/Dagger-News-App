package com.example.daggernewsapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.daggernewsapplication.R
import com.example.daggernewsapplication.databinding.ActivityMainBinding
import com.example.daggernewsapplication.di.MyApplication
import com.example.daggernewsapplication.ui.adapter.NewsAdapter
import com.example.daggernewsapplication.ui.viewmodels.MainViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    /**
     * @author Abdul Qadir
     */

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter : NewsAdapter
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: MainViewModel by viewModels {
        viewModelFactory
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        adapter = NewsAdapter()

        //Dagger Inject
        (application as MyApplication).appComponent?.inject(this)


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
        viewModel.articles.observe(this) {
            adapter.setItems(it)
            binding.recyclerView!!.adapter = adapter
            binding.progressBar.visibility = View.GONE
        }
    }
}