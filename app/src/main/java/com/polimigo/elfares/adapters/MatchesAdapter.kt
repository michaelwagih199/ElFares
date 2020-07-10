package com.polimigo.elfares.adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.polimigo.elfares.R
import com.polimigo.elfares.entities.matches.MachesModel
import com.squareup.picasso.Picasso

class MainMachesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(
        context: Context,
        model: MachesModel,
        clickListener: OnMatchesItemClickListener
    ) {
        val tvLeague = itemView.findViewById<TextView>(R.id.tvLeague) as TextView
        val tvNameT1 = itemView.findViewById<TextView>(R.id.tvNameT1) as TextView
        val tvNameT2 = itemView.findViewById<TextView>(R.id.tvNameT2) as TextView
        val tvDay = itemView.findViewById<TextView>(R.id.tvDay) as TextView
        val tvTime = itemView.findViewById<TextView>(R.id.tvTime) as TextView
        val imgVT1Logo = itemView.findViewById<ImageView>(R.id.imgVT1Logo) as ImageView
        val imgVT2Logo = itemView.findViewById<ImageView>(R.id.imgVT2Logo) as ImageView

        tvLeague.text = model.league
        tvDay.text = model.day
        tvTime.text = model.time
        tvNameT1.text = model.teams!!.team1?.name
        tvNameT2.text = model.teams!!.team2?.name
        // load the image with Picasso
        Picasso
            .with(context) // give it the context
            .load(model.teams.team1?.logo) // load the image
            .into(imgVT1Logo) // select the ImageView to load it into
        // load the image with Picasso
        Picasso
            .with(context) // give it the context
            .load(model.teams.team2?.logo) // load the image
            .into(imgVT2Logo) // select the ImageView to load it into

        itemView.setOnClickListener {
            clickListener.OnMatchesItemClickListener(model)
        }

    }
}

class RecyclerMachesAdapter(
    var context: Context,
    var news: MutableList<MachesModel>,
    val itemClickListener: OnMatchesItemClickListener) : RecyclerView.Adapter<MainMachesHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): MainMachesHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.maches_main_row, parent, false)
        return MainMachesHolder(view)
    }

    override fun getItemCount(): Int {
        return news.size
    }

    override fun onBindViewHolder(myHolder: MainMachesHolder, position: Int) {
        val news = news.get(position)
        myHolder.bind(context, news, itemClickListener)
    }

}


interface OnMatchesItemClickListener {
    fun OnMatchesItemClickListener(machesModel: MachesModel)
}


