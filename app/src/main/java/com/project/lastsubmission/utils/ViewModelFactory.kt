package com.project.lastsubmission.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.lastsubmission.data.source.CatalogRepository
import com.project.lastsubmission.ui.content.favorite.FavoriteViewModel
import com.project.lastsubmission.ui.detail.DetailViewModel
import com.project.lastsubmission.ui.home.HomeViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val catalogRepository: CatalogRepository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when{
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(catalogRepository) as T
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> DetailViewModel(catalogRepository) as T
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> FavoriteViewModel(catalogRepository) as T
            else -> throw Throwable("Unknow ViewModel class ${modelClass.name}")
        }

}