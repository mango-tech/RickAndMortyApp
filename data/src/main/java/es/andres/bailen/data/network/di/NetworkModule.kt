package es.andres.bailen.data.network.di

import es.andres.bailen.data.BuildConfig
import es.andres.bailen.data.network.api.provideRickyMortyApi
import okhttp3.OkHttpClient
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule: Module = module {
    factory { provideOkHttpClient() }
    single { provideRetrofit(get()) }

    //Apis
    factory { provideRickyMortyApi(get()) }

}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(BuildConfig.API_URL).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}


fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient().newBuilder().build()
}