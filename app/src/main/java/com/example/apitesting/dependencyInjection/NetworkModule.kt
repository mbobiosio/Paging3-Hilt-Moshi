package com.example.apitesting.dependencyInjection

import com.example.apitesting.data.api.TestingApi
import com.example.testingapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule
{


    @Singleton
    @Provides
    fun  provideInterceptor() : Interceptor = object : Interceptor
    {
        override fun intercept(chain: Interceptor.Chain): Response =
            chain.run {
                proceed(
                    request()
                        .newBuilder()
                        .addHeader("Authorization", "Bearer " + Constants.AUTH_TOKEN)
                        .build()
                )
            }
    }


    @Singleton
    @Provides
    fun provideHttpClient(interceptor: Interceptor) : OkHttpClient = OkHttpClient.Builder()
        .readTimeout(15, TimeUnit.SECONDS)
        .connectTimeout(15, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        .build()


    @Singleton
    @Provides
    fun provideMoshiConverterFactory() : MoshiConverterFactory = MoshiConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, moshiConverterFactory: MoshiConverterFactory) : Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(moshiConverterFactory)
        .client(okHttpClient)
        .build()


    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): TestingApi = retrofit .create(TestingApi::class.java)



} // NetworkModule closed