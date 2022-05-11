package com.sohaib.newsapp.ui

import androidx.lifecycle.ViewModel
import com.sohaib.newsapp.repository.NewsRepository

class NewsViewModel(
    val newsRepository: NewsRepository
) : ViewModel() {
}