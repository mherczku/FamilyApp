package hu.hm.familyapp.data.remote

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.hm.familyapp.BuildConfig
import java.util.prefs.Preferences
import javax.inject.Singleton
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        return OkHttpClient.Builder()
            .addInterceptor(
                httpLoggingInterceptor.apply {
                    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .addInterceptor(ReceivedCookiesInterceptor())
            .addInterceptor(AddCookiesInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideFamilyAPI(retrofit: Retrofit): FamilyAPI = retrofit.create(FamilyAPI::class.java)
}

class ReceivedCookiesInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse: Response = chain.proceed(chain.request())
        if (originalResponse.headers("Set-Cookie").isNotEmpty()) {
            val cookies = originalResponse.headers("Set-Cookie")
            Timber.wtf(cookies[0])
            Preferences.userRoot().put("cookie", cookies[0])
            println(cookies[0])
        }
        return originalResponse
    }
}

class AddCookiesInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val builder: Request.Builder = chain.request().newBuilder()
        val cookie = Preferences.userRoot().get("cookie", "")
        if (cookie.isNotEmpty()) {
            println("Cookie ->$cookie")
            builder.addHeader("Cookie", cookie)
            Timber.tag("OkHttp").d("Adding Header: %s", cookie)
        } else Timber.wtf("ERROR: NO COOKIE ADDED")
        // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
        return chain.proceed(builder.build())
    }
}
