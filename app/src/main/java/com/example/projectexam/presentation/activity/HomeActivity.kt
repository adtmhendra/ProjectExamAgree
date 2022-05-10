package com.example.projectexam.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.example.projectexam.R
import com.example.projectexam.domain.entity.LatestGameEntity
import com.example.projectexam.domain.entity.SearchGameEntity
import com.example.projectexam.domain.entity.TopRatingEntity
import com.example.projectexam.presentation.LatestGameHomeView
import com.example.projectexam.presentation.SearchGameHomeView
import com.example.projectexam.presentation.TopRatingHomeView
import com.example.projectexam.presentation.adapter.LatestGameAdapter
import com.example.projectexam.presentation.adapter.TopRatingAdapter
import com.example.projectexam.presentation.presenter.LatestGamePresenter
import com.example.projectexam.presentation.presenter.TopRatingPresenter
import com.example.projectexam.presentation.state.LatestGameState
import com.example.projectexam.presentation.state.SearchGameState
import com.example.projectexam.presentation.state.TopRatingState
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

class HomeActivity : DaggerAppCompatActivity(), TopRatingHomeView, LatestGameHomeView,
    SearchGameHomeView {

    @Inject
    lateinit var topRatingPresenter: TopRatingPresenter

    @Inject
    lateinit var latestGamePresenter: LatestGamePresenter

//    @Inject
//    lateinit var searchGamePresenter: SearchGamePresenter

    private var topRatingAdapter: TopRatingAdapter? = null
    private var latestGameAdapter: LatestGameAdapter? = null
    private var isLoading: Boolean = false
    private var currentPage: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        topRatingPresenter.getApiTopRating()
        latestGamePresenter.getApiLatest()
//        searchGamePresenter.getApiSearch("sims")

        getQuery()
    }

    override fun onDestroy() {
        super.onDestroy()
        topRatingPresenter.onDetach()
        latestGamePresenter.onDetach()
    }

    override fun onShowLoading() {
        pb_home.visibility = View.VISIBLE
    }

    override fun onHideLoading() {
        pb_home.visibility = View.GONE
        rv_top_rating.visibility = View.VISIBLE
    }

    override fun onSuccess(entity: SearchGameEntity) {
        Log.d("SearchGameViewModel", "Query ditemukan : ${entity.results.toString()}")
    }

    override fun onSuccess(entity: LatestGameEntity) {
        latestGameAdapter = LatestGameAdapter(entity.results.toMutableList())
        rv_latest_game.addItemDecoration(
            DividerItemDecoration(
                this@HomeActivity,
                DividerItemDecoration.VERTICAL
            )
        )
        rv_latest_game.adapter = latestGameAdapter
        rv_latest_game.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        rv_latest_game.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount

                if (visibleItemCount.plus(firstVisibleItemPosition) > totalItemCount) {
                    latestGameAdapter?.showLoading()
                    isLoading = true
                    currentPage++
                    latestGamePresenter.loadMore(currentPage)
                }
            }
        })
    }

    override fun onSuccess(entity: TopRatingEntity) {
        topRatingAdapter = TopRatingAdapter(entity.results.toMutableList())
        rv_top_rating.addItemDecoration(
            DividerItemDecoration(
                this@HomeActivity,
                DividerItemDecoration.VERTICAL
            )
        )
        rv_top_rating.adapter = topRatingAdapter
        rv_top_rating.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        rv_top_rating.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount

                if (visibleItemCount.plus(firstVisibleItemPosition) > totalItemCount) {
                    topRatingAdapter?.showLoading()
                    isLoading = true
                    currentPage++
                    topRatingPresenter.loadMore(currentPage)
                }
            }
        })
    }

    override fun onError(error: Throwable) {
        Log.e(HomeActivity::class.java.simpleName, "${error.printStackTrace()}")
    }

    override fun onPaginationSuccess(entity: SearchGameEntity) {
        TODO("Not yet implemented")
    }

    override fun onPaginationSuccess(entity: LatestGameEntity) {
        hideLoading()
        latestGameAdapter?.loadMore(entity.results.toMutableList())
    }

    override fun onPaginationSuccess(entity: TopRatingEntity) {
        hideLoading()
        topRatingAdapter?.loadMore(entity.results.toMutableList())
    }

    override fun onPaginationError(error: Throwable) {
        Log.e(HomeActivity::class.java.simpleName, "${error.printStackTrace()}")
        currentPage--
        hideLoading()
    }

    override fun getApiSearch(keyword: String) {
        TODO("Not yet implemented")
    }

    override val searchGameState: LiveData<SearchGameState>
        get() = TODO("Not yet implemented")

    override fun getApiTopRating() {
        TODO("Not yet implemented")
    }

    override val states: LiveData<TopRatingState>
        get() = TODO("Not yet implemented")

    override fun getApiLatest() {
        TODO("Not yet implemented")
    }

    override val latestGameState: LiveData<LatestGameState>
        get() = TODO("Not yet implemented")

    private fun hideLoading() {
        topRatingAdapter?.hideLoading()
        isLoading = false
    }

    private fun hideLoadingLatestGame() {
        latestGameAdapter?.hideLoading()
        isLoading = false
    }

    private fun getQuery() {
        sv_home.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                sv_home.clearFocus()
                if (query.isEmpty()) return false
                goToSearchFragment(query = query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun goToSearchFragment(query: String) {
        val intent = Intent(this, SearchActivity::class.java)
        intent.putExtra(SearchActivity.QUERY, query)
        startActivity(intent)
    }
}