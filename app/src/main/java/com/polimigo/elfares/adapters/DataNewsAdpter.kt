package com.polimigo.elfares.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.polimigo.elfares.R
import com.polimigo.elfares.entities.NewsModel

class DataNewsAdpter(private var dataList: List<NewsModel>, private val context: Context,
                     val itemChannelsClickListener: OnItemNewsClickListener) :
    RecyclerView.Adapter<DataNewsAdpter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.news_item, parent, false))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val news = dataList.get(position)
        holder.bind(context, news, itemChannelsClickListener)
    }


    class ViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView) {
        fun bind(
            context: Context,
            model: NewsModel,
            channelsClickListener: OnItemNewsClickListener
        ) {
            val txtVNewsLabel =
                itemView.findViewById<TextView>(R.id.txtVNewsLabel) as TextView
            val imgVNewsLogo = itemView.findViewById<ImageView>(R.id.imgVNewsLogo) as ImageView

            txtVNewsLabel.text = model.title
            // load the image with Picasso
            Glide
                .with(context) // give it the context
                .load(model.pic) // load the image
                .error(R.drawable.ic_baseline_error_24)
                .placeholder(R.drawable.ic_baseline_image_24)
                .into(imgVNewsLogo) // select the ImageView to load it into

            itemView.setOnClickListener {
                channelsClickListener.onItemClicked(model)
            }


        }

    }

    interface OnItemNewsClickListener {
        fun onItemClicked(newsAdapter: NewsModel)
    }

}

