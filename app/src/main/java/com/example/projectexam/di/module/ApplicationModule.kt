package com.example.projectexam.di.module

import com.example.projectexam.domain.executor.JobExecutor
import com.example.projectexam.domain.executor.UIThread
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    @Singleton
    fun providesJobExecutor(): JobExecutor = JobExecutor()

    @Provides
    @Singleton
    fun providesUIThread(): UIThread = UIThread()
}