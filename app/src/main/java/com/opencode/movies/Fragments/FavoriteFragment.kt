package com.opencode.movies.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.opencode.movies.DataBase.AppDatabase
import com.opencode.movies.Adapters.FavoriteAdapter
import com.opencode.movies.MainActivity
import com.opencode.movies.Models.FavoriteModel
import com.opencode.movies.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FavoriteFragment : Fragment(), FavoriteAdapter.Clicker, FavoriteAdapter.longClicker{

    private lateinit var toolbar: ActionBar

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        toolbar = (activity as AppCompatActivity?)!!.supportActionBar!!
        toolbar.title = "Избранное"

        val v = inflater.inflate(R.layout.fragment_favorite, container, false)

        val recyclerView = v.findViewById<RecyclerView>(R.id.rcvFavorite)
        val favAdapter = FavoriteAdapter(arrayListOf(), this, requireContext(), this)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = favAdapter

        AppDatabase.getDataBase(requireContext()).dao().all
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { favorite->
                favAdapter.addFav(favorite)
            }
        return v
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity?)!!.hideUpButton()
    }

    override fun OnClick(company: FavoriteModel) {
        val bundle = Bundle()
        bundle.putString("id", company.id.toString())
        bundle.putString("name", company.title)
        bundle.putString("image_url", company.image_url)
        bundle.putString("voteAverage", company.voteAverage)
        bundle.putString("releaseDate", company.releaseDate)
        findNavController().navigate(R.id.detailsFragment, bundle)
    }

    override fun onItemLongClicked(company: FavoriteModel, position: Int): Boolean {
        AppDatabase.getDataBase(requireContext()).dao().delete(company)
        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
        return true
    }
}