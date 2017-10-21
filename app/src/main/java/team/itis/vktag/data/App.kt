package team.itis.vktag.data

import android.app.Application
import android.content.Context
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKAccessTokenTracker
import com.vk.sdk.VKSdk
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class App : Application() {
    private var retrofit: Retrofit? = null

    var vkAccessTokenTracker: VKAccessTokenTracker = object : VKAccessTokenTracker() {
        override fun onVKAccessTokenChanged(oldToken: VKAccessToken?, newToken: VKAccessToken?) {
            if (newToken == null) {
                // VKAccessToken is invalid
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        vkAccessTokenTracker.startTracking()
        VKSdk.initialize(this)
        val client = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    var request = chain.request()
                    val vk_user_id = getVkUserId()
                    val url = request.url()
                            .newBuilder()
                            .addQueryParameter("vk_user_id", vk_user_id)
                            .build()

                    request = request
                            .newBuilder()
                            .url(url)
                            .build()
                    chain.proceed(request)
                }.build()

        retrofit = Retrofit.Builder()
                .baseUrl("https://static.itis.team")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        tagApi = retrofit!!.create(TagVKApi::class.java)
    }

    private fun getVkUserId(): String? {
        val sPref = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        return sPref.getString("userId", "")
    }

    companion object {
        var tagApi: TagVKApi? = null
            private set
    }
}