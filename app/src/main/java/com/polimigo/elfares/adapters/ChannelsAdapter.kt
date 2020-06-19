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
import com.squareup.picasso.Picasso


class ChannelsAdapter(
    val context: Context,
    val models: ArrayList<ChannelsModel>

) :
    RecyclerView.Adapter<ChannelsAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChannelsAdapter.ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.channels_item, parent, false)
        return ItemViewHolder(view)
    }


    override fun onBindViewHolder(holder: ChannelsAdapter.ItemViewHolder, position: Int) {
        models[position]?.let {
            holder.bindItems(context, it)

        }
    }

    override fun getItemCount(): Int {
        return models.size
    }//


    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("NewApi")
        fun bindItems(context: Context, model: ChannelsModel) {

            val txtVChannelLabel =
                itemView.findViewById<TextView>(R.id.txtVChannelLabel) as TextView
            val imgVchannelLogo =
                itemView.findViewById<ImageView>(R.id.imgVchannelLogo) as ImageView

            txtVChannelLabel.text = model.name
            // load the image with Picasso
            Picasso
                .with(context) // give it the context
                .load(model.pic) // load the image
                .into(imgVchannelLogo) // select the ImageView to load it into
        }

    }


}

