package com.opencode.movies.Fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.opencode.movies.Adapters.MovieAdapter
import com.opencode.movies.Adapters.SearchAdapter
import com.opencode.movies.MainActivity
import com.opencode.movies.Models.Result
import com.opencode.movies.Models.SearchModel
import com.opencode.movies.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment(), SearchAdapter.Clicker {

    private lateinit var  searchview : SearchView
    var mCompositeDisposable: CompositeDisposable? = CompositeDisposable()
    private var layoutManager: GridLayoutManager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_search, container, false)


        val toolbar = (activity as AppCompatActivity?)!!.supportActionBar!!
        toolbar.title = "Поиск"

        val query = requireArguments().getString("query")

        loadResults(query!!)

        return v
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity?)!!.showUpButton()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
        val menuItem = menu.findItem(R.id.action_search)

        searchview = menuItem.actionView as SearchView

        searchview.queryHint = "search"

        searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                loadResults(query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun loadResults(query: String) {
        mCompositeDisposable?.add(
            RetrofitApi.getRetrofit()!!.getSearchResult("ru",getString(R.string.api_key),query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::loadRecyclerView, this::initError)
        )
    }

    private fun loadRecyclerView(companies: SearchModel){
        loader.visibility = View.GONE
        val adapter = context?.let { SearchAdapter(companies.results, this, it) }
        layoutManager = GridLayoutManager(context, 1)
        rcvSearch?.layoutManager = layoutManager
        rcvSearch?.adapter = adapter
    }

    private fun initError(error: Throwable){
        println(error)
    }


    override fun onClick(company: Result) {
        val bundle = Bundle()
        bundle.putString("id", company.id.toString())
        bundle.putString("name", company.title)
        bundle.putString("image_url", company.posterPath)
        bundle.putString("voteAverage", company.voteAverage.toString())
        bundle.putString("releaseDate", company.releaseDate)
        findNavController().navigate(R.id.detailsFragment, bundle)
    }
}