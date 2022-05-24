package com.sohaib.newsapp.repository

import com.sohaib.newsapp.db.ArticleDatabase
import com.sohaib.newsapp.models.Article

class NewsRepository(
    private val db: ArticleDatabase
) {
    fun getBreakingNews() = BreakingNewsPagingSource()

    fun searchNews(searchQuery: String) = SearchNewsPagingSource(searchQuery)

    suspend fun upsert(article: Article) = db.getArticleDao().upsert(article)

    fun getSavedNews() = SavedNewsPagingSource(db)
    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)
}