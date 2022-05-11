package com.example.projectexam.di.builder

import com.example.projectexam.di.module.HomeModule
import com.example.projectexam.di.module.SearchModule
import com.example.projectexam.di.scope.Presentation
import com.example.projectexam.presentation.activity.HomeActivity
import com.example.projectexam.presentation.activity.SearchActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @Presentation
    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun contributeHomeActivity(): HomeActivity

    @Presentation
    @ContributesAndroidInjector(modules = [SearchModule::class])
    abstract fun contributeSearchActivity(): SearchActivity
}