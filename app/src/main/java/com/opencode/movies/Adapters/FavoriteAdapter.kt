package com.opencode.movies.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.opencode.movies.Models.FavoriteModel
import com.opencode.movies.R


class FavoriteAdapter(favorite:ArrayList<FavoriteModel>, var listener: Clicker, var context: Context, var longListener: longClicker): RecyclerView.Adapter<FavoriteAdapter.RecyclerViewHolder>() {

    private var listFavorite: List<FavoriteModel> = favorite

    interface Clicker{
        fun OnClick(company: FavoriteModel)
    }

    interface longClicker{
        fun onItemLongClicked(company: FavoriteModel, position: Int): Boolean
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_item,parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val current: FavoriteModel = listFavorite[position]

        holder.mName.text= current.title
        holder.tvRating.text = "${current.voteAverage}/10"
        holder.tvTime.text = current.releaseDate
        holder.mImage?.let { Glide.with(context).load("https://image.tmdb.org/t/p/w500${current.image_url}").into(it) }

        holder.cardView.setOnClickListener { listener.OnClick(current) }
        holder.cardView.setOnLongClickListener { longListener.onItemLongClicked(current, position) }


    }

    override fun getItemCount(): Int {
        return listFavorite.size
    }

    fun removeItem(position: Int) {
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, listFavorite.size)
    }

    fun addFav( listFavorite : List<FavoriteModel>){
        this.listFavorite = listFavorite
        notifyDataSetChanged()
    }

    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cardView = itemView.findViewById(R.id.cardViewCategory) as CardView
        var mName = itemView.findViewById<TextView>(R.id.tvName)
        var mImage = itemView.findViewById<ImageView>(R.id.ivIcon)
        var tvTime = itemView.findViewById(R.id.tvTime) as TextView
        var tvRating = itemView.findViewById(R.id.tvRating) as TextView
    }

}