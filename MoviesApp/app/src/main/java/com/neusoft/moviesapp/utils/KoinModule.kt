package com.neusoft.moviesapp.utils

import androidx.lifecycle.SavedStateHandle
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.neusoft.moviesapp.data.MovieApi
import com.neusoft.moviesapp.data.local.SuggestionDatabase
import com.neusoft.moviesapp.data.repository.MovieRepository
import com.neusoft.moviesapp.data.repository.SuggestionRepository
import com.neusoft.moviesapp.homescreen.HomeScreenViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val homeScreenViewModelModule = module {
    viewModel {
        HomeScreenViewModel(get(), get(), get())
    }
    single { SavedStateHandle() }
}

val retrofitModule = module {

    fun provideApiKeyInterceptor(): ApiKeyInterceptor = ApiKeyInterceptor()

    fun provideHttpClient(apiKeyInterceptor: ApiKeyInterceptor): OkHttpClient =
        OkHttpClient.Builder().apply {
            this.addInterceptor(apiKeyInterceptor)
        }.build()

    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client).build()

    fun provideMovieApi(retrofit: Retrofit): MovieApi = retrofit.create(MovieApi::class.java)

    single { provideApiKeyInterceptor() }
    single { provideGson() }
    single { provideHttpClient(get()) }
    single { provideRetrofit(get(), get()) }
    single { provideMovieApi(get()) }
}


val movieRepositoryModule = module {
    factory { MovieRepository(get()) }
}

val roomModule = module {
    single {
        Room.databaseBuilder(get(), SuggestionDatabase::class.java, "suggestions_db")
            .fallbackToDestructiveMigration().build()
    }
    single { get<SuggestionDatabase>().suggestionDao() }
    single { SuggestionRepository(get()) }

}