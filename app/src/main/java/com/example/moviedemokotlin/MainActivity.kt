package com.example.moviedemokotlin

import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.moviedemokotlin.ui.PopularMoviesFragment
import com.example.moviedemokotlin.ui.NowPlayingMoviesFragment
import com.example.moviedemokotlin.ui.SearchFragment
import com.example.moviedemokotlin.ui.TopRatedMoviesFragment
import com.example.moviedemokotlin.viewmodel.MoviesViewModel
import com.example.moviedemokotlin.viewmodel.MoviesViewModelFactory
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.setLogo(R.drawable.tool_bar_logo)

        val stringBuilder = SpannableStringBuilder("MovLancer")
        stringBuilder.setSpan(ForegroundColorSpan(Color.WHITE), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        stringBuilder.setSpan(ForegroundColorSpan(Color.GREEN), 3, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        toolbar.title = stringBuilder
        setSupportActionBar(toolbar)

        when {
            supportFragmentManager.findFragmentByTag(PopularMoviesFragment.TAG) != null -> {
                movie_category.text = this.getString(R.string.mostPopularMovies)
            }
            supportFragmentManager.findFragmentByTag(TopRatedMoviesFragment.TAG) != null -> {
                movie_category.text = this.getString(R.string.topRatedMovies)
            }
            supportFragmentManager.findFragmentByTag(NowPlayingMoviesFragment.TAG) != null -> {
                movie_category.text = this.getString(R.string.nowPlayingMovies)
            }
            else -> supportFragmentManager.beginTransaction()
                    .replace(R.id.container, PopularMoviesFragment.create(), PopularMoviesFragment.TAG)
                    .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_main, menu)
        val menuItem = menu!!.findItem(R.id.action_search)
        searchView = menuItem.actionView as SearchView

        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
//                Toast.makeText(this@MainActivity,p0,Toast.LENGTH_SHORT).show()
                val bundle = Bundle()
                bundle.putString("QueryKey", query)
                val searchFragment = SearchFragment.create()
                searchFragment.arguments = bundle
                if (supportFragmentManager.findFragmentByTag(SearchFragment.TAG) == null) {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.container, searchFragment,SearchFragment.TAG)
                            .commit()
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item!!.itemId) {
            android.R.id.home -> {
                this.onBackPressed()
                true
            }
            R.id.most_popular -> {
                movie_category.text = this.getString(R.string.mostPopularMovies)
                if (supportFragmentManager.findFragmentByTag(PopularMoviesFragment.TAG) == null) {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.container, PopularMoviesFragment.create(),PopularMoviesFragment.TAG)
                            .commit()
                }
                true
            }
            R.id.top_rated -> {
                movie_category.text = this.getString(R.string.topRatedMovies)
                if (supportFragmentManager.findFragmentByTag(TopRatedMoviesFragment.TAG) == null) {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.container, TopRatedMoviesFragment.create(),TopRatedMoviesFragment.TAG)
                            .commit()
                }
                true
            }
            R.id.now_playing -> {
                movie_category.text = this.getString(R.string.nowPlayingMovies)
                if (supportFragmentManager.findFragmentByTag(NowPlayingMoviesFragment.TAG) == null) {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.container, NowPlayingMoviesFragment.create(),NowPlayingMoviesFragment.TAG)
                            .commit()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector
}
