package com.polimigo.elfares.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.polimigo.elfares.R
import com.polimigo.elfares.entities.NewsModel
import com.squareup.picasso.Picasso


class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(
        context: Context,
        model: NewsModel,
        clickListener: OnItemClickListener
    ) {
        val txtVChannelLabel =
            itemView.findViewById<TextView>(R.id.txtVChannelLabel) as TextView
        val imgVchannelLogo = itemView.findViewById<ImageView>(R.id.imgVchannelLogo) as ImageView

        txtVChannelLabel.text = model.title
        // load the image with Picasso
        Picasso
            .with(context) // give it the context
            .load(model.pic) // load the image
            .into(imgVchannelLogo) // select the ImageView to load it into

        itemView.setOnClickListener {
            clickListener.onItemClicked(model)
        }


    }
}


    class RecyclerAdapter(var context: Context,
                          var news: MutableList<NewsModel>,
                          val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<MyHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, position: Int): MyHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.channels_item, parent, false)
            return MyHolder(view)
        }

        override fun getItemCount(): Int {
            return news.size
        }

        override fun onBindViewHolder(myHolder: MyHolder, position: Int) {
            val news = news.get(position)
            myHolder.bind(context,news,itemClickListener)
        }
    }


    interface OnItemClickListener {
        fun onItemClicked(newsAdapter: NewsModel)
    }
