package com.opencode.movies

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.opencode.movies.Fragments.FavoriteFragment
import com.opencode.movies.Fragments.ProfileFragment
import com.opencode.movies.Fragments.SearchFragment



//Архитектура приложения отсутствует (mvc).
//На данный момент изучаю MVVM, но пока плохо ориентируюсь.
//В полной мере к заданию смог приступить только в субботу.
//
class MainActivity : AppCompatActivity() {
    val fragment1: Fragment = FavoriteFragment()
    val fragment2: Fragment = ProfileFragment()
    val fragment3: Fragment = SearchFragment()
    private lateinit var  searchview : androidx.appcompat.widget.SearchView

    val fm: FragmentManager = supportFragmentManager
    var active: Fragment = fragment1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navigation =
            findViewById<View>(R.id.navigation) as BottomNavigationView
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        fm.beginTransaction().replace(R.id.main_container, fragment1, "1").commit()

    }

    private val mOnNavigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener =
        object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(@NonNull item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.navigation_call -> {
                        fm.beginTransaction().replace(R.id.main_container, fragment1).commit()
                        active = fragment1
                        hideUpButton()
                        return true
                    }
                    R.id.navigation_chat -> {
                        fm.beginTransaction().replace(R.id.main_container, fragment2).commit()
                        active = fragment2
                        hideUpButton()
                        return true
                    }
                }
                return false
            }
        }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val menuItem = menu.findItem(R.id.action_search)

        searchview = menuItem.actionView as androidx.appcompat.widget.SearchView
        searchview.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val bundle = Bundle()
                bundle.putString("query", query)
                fragment3.arguments = bundle
                fm.beginTransaction().replace(R.id.main_container, fragment3).addToBackStack(null).commit()

                val inputManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.SHOW_FORCED)

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    fun showUpButton() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun hideUpButton() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
    }

    override fun onBackPressed() {

        val backStackCount = supportFragmentManager.backStackEntryCount

        if (backStackCount >= 1) {
            supportFragmentManager.popBackStack()
            if (backStackCount == 1) {
                hideUpButton()
            }
        } else {
            super.onBackPressed()
        }
    }
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home ->{
            onBackPressed()
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}

