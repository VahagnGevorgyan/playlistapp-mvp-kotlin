package com.playlistappkotlin.inject.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.playlistappkotlin.BuildConfig
import com.playlistappkotlin.data.network.api.ApiHelper
import com.playlistappkotlin.data.network.api.ApiInterface
import com.playlistappkotlin.data.network.session.Session
import com.playlistappkotlin.inject.qualifier.ApiHttpClient
import com.playlistappkotlin.inject.qualifier.ApiInfo
import com.playlistappkotlin.inject.qualifier.ApiUrl
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    internal fun provideData(apiInterface: ApiInterface, session: Session, @ApiUrl baseUrl: HttpUrl): ApiHelper {
        return ApiHelper(apiInterface, session, baseUrl)
    }

    @Provides
    @Singleton
    internal fun provideSession(@ApiUrl baseUrl: HttpUrl, @ApiInfo apiKey: String): Session {
        return Session(baseUrl.toString(), apiKey)
    }

    @Provides
    internal fun provideApiService(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(
            @ApiUrl baseUrl: HttpUrl,
            @ApiHttpClient client: OkHttpClient,
            gson: Gson): Retrofit {

        return Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    @ApiUrl
    internal fun provideApiUrl(): HttpUrl {
        return HttpUrl.parse(BuildConfig.BASE_URL)!!
    }

    @Provides
    @ApiInfo
    internal fun provideApiKey(): String {
        return BuildConfig.API_KEY
    }

    @Provides
    internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        }
        return interceptor
    }

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        return GsonBuilder()
                .setLenient()
                .create()
    }

    @Provides
    @Singleton
    @ApiHttpClient
    internal fun provideHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor).build()
    }
}
