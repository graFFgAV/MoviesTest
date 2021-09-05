package com.opencode.movies.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.opencode.movies.Models.Result
import com.opencode.movies.R
import com.squareup.picasso.Picasso


class SearchAdapter(val companies: ArrayList<Result>, var listener: Clicker, var context: Context): RecyclerView.Adapter<SearchAdapter.ViewHolder>() {


    interface Clicker{
        fun onClick(company: Result)
    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.movie_search_item, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  companies.size
    }

    @SuppressLint("SetTextI18n", "DefaultLocale")
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

        p0.insertAddons(companies[p1], listener)
        p0.name?.text = companies[p1].title
        p0.tvRating?.text = "${companies[p1].voteAverage}/10"
        p0.tvTime?.text = companies[p1].releaseDate
        p0.tvDiscr?.text = companies[p1].overview

        var imageUrl = "https://image.tmdb.org/t/p/w500${companies[p1].posterPath}"


        Picasso.get()
            .load(imageUrl)
            .error(R.drawable.noposter)
            .into(p0.imageLogo)


       // p0.imageLogo?.let { Glide.with(context).load(imageUrl).into(it) }

        p0.cardView?.setOnClickListener { listener.onClick(companies[p1]) }

    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTime: TextView? = null
        var tvRating: TextView? = null
        var tvDiscr: TextView? = null
        var name: TextView? = null
        var cardView: ConstraintLayout? = null
        var imageLogo: ImageView? = null
        @SuppressLint("NewApi", "WrongViewCast")
        fun insertAddons(companies: Result, listener: Clicker){
            name = itemView.findViewById(R.id.tvName) as TextView
            cardView = itemView.findViewById(R.id.cardViewCategory) as ConstraintLayout
            imageLogo = itemView.findViewById(R.id.ivIcon) as ImageView
            tvTime = itemView.findViewById(R.id.tvTime) as TextView
            tvRating = itemView.findViewById(R.id.tvRating) as TextView
            tvDiscr = itemView.findViewById(R.id.textDiscr) as TextView
        }
    }
}
