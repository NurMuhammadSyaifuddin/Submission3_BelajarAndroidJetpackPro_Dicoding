package com.project.lastsubmission.di

import com.project.lastsubmission.di.home.HomeFragmentBuildersModule
import com.project.lastsubmission.ui.detail.DetailActivity
import com.project.lastsubmission.ui.home.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = [HomeFragmentBuildersModule::class])
    abstract fun contributeHomeActivity(): HomeActivity

    @ContributesAndroidInjector
    abstract fun contributeDetailActivity(): DetailActivity

}