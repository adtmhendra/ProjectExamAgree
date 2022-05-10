package com.example.projectexam.presentation.activity

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectexam.R
import com.example.projectexam.domain.entity.SearchGameEntity
import com.example.projectexam.presentation.SearchGameHomeView
import com.example.projectexam.presentation.adapter.SearchGameAdapter
import com.example.projectexam.presentation.presenter.SearchGamePresenter
import com.example.projectexam.presentation.state.SearchGameState
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_search.*
import javax.inject.Inject

class SearchActivity : DaggerAppCompatActivity(), SearchGameHomeView {

    @Inject
    lateinit var searchGamePresenter: SearchGamePresenter

    private lateinit var getQuery: String

    private var searchGameAdapter: SearchGameAdapter? = null
    private var isLoading: Boolean = false
    private var currentPage: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        getQuery = intent.getStringExtra(QUERY).toString()

        searchGamePresenter.getApiSearch(getQuery)
        Log.d("SearchActivity", getQuery)
    }

    override fun onDestroy() {
        super.onDestroy()
        searchGamePresenter.onDetach()
    }

    override fun onShowLoading() {
//        TODO("Not yet implemented")
    }

    override fun onHideLoading() {
//        TODO("Not yet implemented")
    }

    override fun onSuccess(entity: SearchGameEntity) {
        searchGameAdapter = SearchGameAdapter(entity.results.toMutableList())
        rv_search.adapter = searchGameAdapter
        rv_search.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        rv_search.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount

                if (visibleItemCount.plus(firstVisibleItemPosition) > totalItemCount) {
                    searchGameAdapter?.showLoading()
                    isLoading = true
                    currentPage++
                }
            }
        })
        Log.d("SearchActivity", "Sukses")
    }

    override fun onError(error: Throwable) {
//        TODO("Not yet implemented")
    }

    override fun onPaginationSuccess(entity: SearchGameEntity) {
//        TODO("Not yet implemented")
    }

    override fun onPaginationError(error: Throwable) {
//        TODO("Not yet implemented")
    }

    override fun getApiSearch(keyword: String) {
//        TODO("Not yet implemented")
    }

    override val searchGameState: LiveData<SearchGameState>
        get() = TODO("Not yet implemented")

    companion object {
        const val QUERY = "query"
    }
}