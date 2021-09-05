package com.opencode.movies.Fragments

import RetrofitApi
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.opencode.movies.DataBase.AppDatabase
import com.opencode.movies.MainActivity
import com.opencode.movies.Models.FavoriteModel
import com.opencode.movies.Models.FavoriteModelId
import com.opencode.movies.Models.MovieResponse
import com.opencode.movies.R
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.detail_content.*
import kotlinx.android.synthetic.main.fragment_details.*


class DetailsFragment : Fragment() {

    private lateinit var toolbar: ActionBar
    private var mCompositeDisposable: CompositeDisposable? = CompositeDisposable()
    var id_film = 1
    var button_fav: Button? = null

    val positiveButtonClick = { dialog: DialogInterface, which: Int ->
        AppDatabase.getDataBase(requireContext()).dao().deleteById(id_film)
        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
        addFavoritre()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v =  inflater.inflate(R.layout.fragment_details, container, false)
        val id =  requireArguments().getString("id")
        id_film = id!!.toInt()
        toolbar = (activity as AppCompatActivity?)!!.supportActionBar!!
        toolbar.title = requireArguments().getString("name")
        button_fav = v.findViewById(R.id.button_fav)

        checkFavorite()
        loadDetails()
        return v
    }

    @SuppressLint("CheckResult")
    fun checkFavorite(){
        AppDatabase.getDataBase(requireContext()).dao().loadById(id_film)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe (this::setButtonText, this::initErrorR)
    }

    fun setButtonText(id: FavoriteModelId) {
        favoritFilm()
    }

    fun favoritFilm(){
        button_fav!!.text = "В избранном"
        button_fav!!.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            with(builder)
            {
                setTitle("Удалить фильм")
                setMessage("Вы действительно хотите удалить этот фильм из избарнного?")
                setPositiveButton("Да", DialogInterface.OnClickListener(function = positiveButtonClick))
                show()
            }
        }
    }

    fun initErrorR(error: Throwable){
        addFavoritre()
    }
    fun addFavoritre(){
        button_fav!!.text = "Добавить в избранное"
        button_fav!!.setOnClickListener {
            addToFavorite(requireArguments().getString("name").toString(),
                requireArguments().getString("image_url").toString(),
                requireArguments().getString("voteAverage").toString(),
                requireArguments().getString("releaseDate").toString()
            )
        }
    }

    @SuppressLint("CheckResult")
    private fun addToFavorite(name: String?, image: String?, voteAverage:String?, releaseDate:String?) {
        val fm = FavoriteModel(requireArguments().getString("id")?.toInt()!!, name!!, image!!,voteAverage!!,releaseDate!!)
        Single.fromCallable {
            AppDatabase.getDataBase(requireContext()).dao().insert(fm)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(this::showToast, this::showError)
    }

    fun showToast(unit: Unit) {
        Toast.makeText(context, "Добавлено", Toast.LENGTH_SHORT).show()
        favoritFilm()
    }

    fun showError(error: Throwable){
        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
    }
    
    override fun onResume() {
        super.onResume()
        (activity as MainActivity?)!!.showUpButton()
    }

    private fun loadDetails() {
        mCompositeDisposable!!.add(
            (RetrofitApi.getRetrofit())!!.getFilmById(id_film,"ru",getString(R.string.api_key))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::setDetails, this::initError)
        )
    }

    @SuppressLint("SetTextI18n", "InflateParams")
    private fun setDetails(details: MovieResponse){
        loaderDetails.visibility = View.GONE
        content.visibility = View.VISIBLE
        Glide.with(requireContext()).load("https://image.tmdb.org/t/p/w500${requireArguments().getString("image_url")}").into(image_detail)
        title_detail.text = details.title
        type_detail.text = details.tagline
        status.text = "Статус: ${details.status}"
        dlitelnost_detail.text = "Длительность:\n${details.runtime}м"
        score_by.text = details.voteCount.toString()
        score.text = "${details.voteAverage}/10"
        text_detail.text = details.overview
        date_detail.text = "Дата выпуска: ${details.releaseDate}"
        val genres = details.genres
        val inflater = LayoutInflater.from(context)
        for(genre in genres){
            val chip = inflater.inflate(R.layout.genre_chip_item, null, false) as Chip
            chip.text = genre.name
            anime_details_genres.addView(chip)
        }
    }

    private fun initError(error: Throwable){
        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
    }
}




