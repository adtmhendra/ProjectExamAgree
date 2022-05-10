package com.example.projectexam.di.module

import androidx.lifecycle.ViewModel
import com.example.projectexam.data.factory.HomeFactory
import com.example.projectexam.data.source.HomeDatasource
import com.example.projectexam.di.scope.Presentation
import com.example.projectexam.di.scope.ViewModelKey
import com.example.projectexam.domain.executor.JobExecutor
import com.example.projectexam.domain.executor.UIThread
import com.example.projectexam.domain.repository.LatestGameRepository
import com.example.projectexam.domain.repository.LatestGameRepositoryImpl
import com.example.projectexam.domain.repository.TopRatingRepository
import com.example.projectexam.domain.repository.TopRatingRepositoryImpl
import com.example.projectexam.domain.usecase.LatestGameUseCase
import com.example.projectexam.domain.usecase.TopRatingUseCase
import com.example.projectexam.presentation.LatestGameHomeView
import com.example.projectexam.presentation.TopRatingHomeView
import com.example.projectexam.presentation.activity.HomeActivity
import com.example.projectexam.presentation.presenter.LatestGamePresenter
import com.example.projectexam.presentation.presenter.TopRatingPresenter
import com.example.projectexam.presentation.viewmodel.LatestGameViewModel
import com.example.projectexam.presentation.viewmodel.TopRatingViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit

@Module
abstract class HomeModule {

    companion object {

        @Presentation
        @Provides
        fun providesHomeDataSource(retrofit: Retrofit): HomeDatasource =
            retrofit.create(HomeDatasource::class.java)

        @Presentation
        @Provides
        fun providesFactory(datasource: HomeDatasource): HomeFactory =
            HomeFactory(datasource)

        //TopRatingRepoInstance
        @Presentation
        @Provides
        fun providesRepository(factory: HomeFactory): TopRatingRepositoryImpl =
            TopRatingRepositoryImpl(factory)

        //LatestGameRepoInstance
        @Presentation
        @Provides
        fun providesLatestGameRepository(factory: HomeFactory): LatestGameRepositoryImpl =
            LatestGameRepositoryImpl(factory)

//        //SearchGameRepoInstance
//        @Presentation
//        @Provides
//        fun providesSearchGameRepository(factory: HomeFactory): SearchGameRepositoryImpl =
//            SearchGameRepositoryImpl(factory)

        //TopRatingUseCase
        @Presentation
        @Provides
        fun providesUsecase(
            repository: TopRatingRepository,
            executor: JobExecutor,
            thread: UIThread
        ): TopRatingUseCase = TopRatingUseCase(repository, executor, thread)

        //LatestGameUseCase
        @Presentation
        @Provides
        fun providesLatestGameUseCase(
            repository: LatestGameRepository,
            executor: JobExecutor,
            thread: UIThread
        ): LatestGameUseCase = LatestGameUseCase(repository, executor, thread)

//        //SearchGameUseCase
//        @Presentation
//        @Provides
//        fun providesSearchGameUseCase(
//            repository: SearchGameRepository,
//            executor: JobExecutor,
//            thread: UIThread
//        ): SearchGameUseCase = SearchGameUseCase(repository, executor, thread)

        //TopRatingPresenter
        @Presentation
        @Provides
        fun providesTopRatingPresenter(
            view: TopRatingHomeView,
            usecase: TopRatingUseCase
        ): TopRatingPresenter = TopRatingPresenter(view, usecase)

        //LatestGamePresenter
        @Presentation
        @Provides
        fun providesLatestGamePresenter(
            view: LatestGameHomeView,
            usecase: LatestGameUseCase
        ): LatestGamePresenter = LatestGamePresenter(view, usecase)

//        //SearchGamePresenter
//        @Presentation
//        @Provides
//        fun providesSearchGamePresenter(
//            view: SearchGameHomeView,
//            usecase: SearchGameUseCase
//        ): SearchGamePresenter = SearchGamePresenter(view, usecase)
    }

    @Binds
    abstract fun bindRepository(repositoryImpl: TopRatingRepositoryImpl): TopRatingRepository

    @Binds
    abstract fun bindView(activity: HomeActivity): TopRatingHomeView

    @Binds
    @IntoMap
    @ViewModelKey(TopRatingViewModel::class)
    abstract fun bindHomeVieModel(ViewModel: TopRatingViewModel): ViewModel

    @Binds
    abstract fun bindLatestGameRepository(repositoryImpl: LatestGameRepositoryImpl): LatestGameRepository

    @Binds
    abstract fun bindLatestGameHomeView(activity: HomeActivity): LatestGameHomeView

    @Binds
    @IntoMap
    @ViewModelKey(LatestGameViewModel::class)
    abstract fun bindLatestGameViewModel(ViewModel: LatestGameViewModel): ViewModel

//    @Binds
//    abstract fun bindSearchGameRepository(repositoryImpl: SearchGameRepositoryImpl): SearchGameRepository
//
//    @Binds
//    abstract fun bindSearchGameHomeView(activity: HomeActivity): SearchGameHomeView
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(SearchGameViewModel::class)
//    abstract fun bindSearchGameViewModel(ViewModel: SearchGameViewModel): ViewModel
}