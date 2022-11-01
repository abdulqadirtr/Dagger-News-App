package com.example.daggernewsapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.daggernewsapplication.data.model.ArticlesItem
import com.example.daggernewsapplication.databinding.ItemsNewsBinding
import com.squareup.picasso.Picasso

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {

    private lateinit var binding: ItemsNewsBinding

     private val newsList : MutableList<ArticlesItem> = mutableListOf()

    var itemClickListener : (ArticlesItem?) -> Unit = { }

    inner class MyViewHolder(item : ItemsNewsBinding) : RecyclerView.ViewHolder(item.root) {

        fun bind(articlesItem: ArticlesItem) {
            binding.tvTitle.text = articlesItem.title
            binding.tvSource.text = articlesItem.source!!.name
            binding.tvDate.text = articlesItem.publishedAt
            binding.tvDesc.text = articlesItem.description
            val imgUrl = articlesItem.urlToImage
            Picasso.get().load(imgUrl).into(binding.image)
            itemView.setOnClickListener {
                itemClickListener.invoke(articlesItem)
            }
        }
    }

    fun setItems(news : List<ArticlesItem>){
        newsList.clear()
        newsList.addAll(news)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemsNewsBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       holder.bind(newsList[position])
    }

    override fun getItemCount(): Int {
       return newsList.size
    }

}