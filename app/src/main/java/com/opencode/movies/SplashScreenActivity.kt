package com.opencode.movies

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import com.opencode.movies.DataBase.AppDatabase
import com.opencode.movies.Models.FavoriteModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_splash_screen.*


class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val decorView = window.decorView
        val uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        decorView.systemUiVisibility = uiOptions


        addToFavorite()

        motion_layout.setTransitionListener(object : MotionLayout.TransitionListener{
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {

            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {

            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {

                startActivity(Intent(this@SplashScreenActivity,MainActivity::class.java))
                finish()
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
            }
        })

    }

    //куда бы это спрятать, что б никто не видел.
    @SuppressLint("CheckResult")
    private fun addToFavorite() {

        val size = resources.getIntArray(R.array.id).size
        var i = 0
        while (i < size){

            val fm = FavoriteModel(resources.getIntArray(R.array.id)[i], resources.getStringArray(R.array.title)[i], resources.getStringArray(R.array.imageUrl)[i],resources.getStringArray(R.array.voteAverage)[i], resources.getStringArray(R.array.releaseDate)[i])
            i++

            Single.fromCallable {
                AppDatabase.getDataBase(this).dao().insert(fm)
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(this::showRes, this::showError)
        }}

    fun showRes(unit: Unit) {

    }

    private fun showError(error: Throwable){
        println(error)
    }

}