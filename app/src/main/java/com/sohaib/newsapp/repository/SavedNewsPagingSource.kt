package com.sohaib.newsapp.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sohaib.newsapp.api.RetrofitInstance
import com.sohaib.newsapp.db.ArticleDatabase
import com.sohaib.newsapp.models.Article
import com.sohaib.newsapp.util.Constants
import com.sohaib.newsapp.util.Constants.Companion.QUERY_PAGE_SIZE
import retrofit2.HttpException
import java.io.IOException
import kotlin.math.max

private const val STARTING_KEY = 0
private const val TAG = "NewsPagingSource"

class SavedNewsPagingSource(private val db: ArticleDatabase) : PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        Log.d(TAG, "getRefreshKey: ")
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val start = params.key ?: STARTING_KEY

        return try {
            val list = db.getArticleDao().getAllArticles(
                pageSize = QUERY_PAGE_SIZE,
                pageIndex = start
            )
            val nextKey = if (list.isNotEmpty()) {
                start + QUERY_PAGE_SIZE
            } else null
            LoadResult.Page(
                data = list,
                prevKey = getPrevKey(start, params),
                nextKey = nextKey
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    private fun getPrevKey(start: Int, params: LoadParams<Int>) =
        when (start) {
            STARTING_KEY -> null
            else -> params.key
        }
}