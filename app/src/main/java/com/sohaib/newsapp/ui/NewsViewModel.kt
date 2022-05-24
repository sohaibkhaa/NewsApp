package com.sohaib.newsapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.sohaib.newsapp.models.Article
import com.sohaib.newsapp.models.NewsResponse
import com.sohaib.newsapp.repository.NewsRepository
import com.sohaib.newsapp.util.Constants.Companion.QUERY_PAGE_SIZE
import com.sohaib.newsapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {
    val breakingNews: Flow<PagingData<Article>> = Pager(
        config = PagingConfig(pageSize = QUERY_PAGE_SIZE, enablePlaceholders = false, initialLoadSize = QUERY_PAGE_SIZE)
    ) {
        newsRepository.getBreakingNews()
    }
        .flow
        .cachedIn(viewModelScope)

    fun searchNews(searchQuery: String): Flow<PagingData<Article>> = Pager(
        PagingConfig(pageSize = QUERY_PAGE_SIZE, enablePlaceholders = false, initialLoadSize = QUERY_PAGE_SIZE)
    ){
        newsRepository.searchNews(searchQuery)
    }
        .flow
        .cachedIn(viewModelScope)

    fun savedArticle(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article)
    }

    fun getSavedNews(): Flow<PagingData<Article>> = Pager(
        PagingConfig(pageSize = QUERY_PAGE_SIZE, enablePlaceholders = false, initialLoadSize = QUERY_PAGE_SIZE)
    ){
        newsRepository.getSavedNews()
    }
        .flow
        .cachedIn(viewModelScope)

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }

}