package com.polimigo.elfares.adapters


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.polimigo.elfares.R
import com.polimigo.elfares.entities.ChannelsModel
import com.polimigo.elfares.entities.NewsModel
import com.squareup.picasso.Picasso

/**
 * (var users:MutableList<User>, val itemClickListener: OnItemClickListener)
 */


class NewsAdapter(val context: Context,val models: ArrayList<NewsModel>,
                  val itemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<NewsAdapter.ItemViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.channels_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsAdapter.ItemViewHolder, position: Int) {
        val user = models.get(position)
        holder.bindItems(context,user,itemClickListener)
    }

    override fun getItemCount(): Int {
        return models.size
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("NewApi")
        fun bindItems(context: Context, model: NewsModel ,clickListener: OnItemClickListener) {

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

    interface OnItemClickListener{
        fun onItemClicked(newsModel: NewsModel)
    }
}

