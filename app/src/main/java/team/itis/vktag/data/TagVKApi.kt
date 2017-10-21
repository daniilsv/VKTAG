package team.itis.vktag.data

import retrofit2.Call
import retrofit2.http.*


interface TagVKApi {

    @GET("/")
    fun getAdminTags(): Call<ApiResponse>

    @GET("/")
    fun getTag(@Query("hash") hash: String): Call<ApiResponse>

    @FormUrlEncoded
    @POST("/")
    fun updateTag(@Field("hash") hash: String,
                  @Field("title") title: String = "",
                  @Field("type") type: String,
                  @Field("data") data: Any): Call<ApiResponse>


    @FormUrlEncoded
    @PUT("/")
    fun insertTag(@Field("hash") hash: String,
                  @Field("title") title: String = "",
                  @Field("type") type: String,
                  @Field("data") data: Any): Call<ApiResponse>

    @DELETE("/")
    fun deleteTag(@Query("hash") hash: String): Call<ApiResponse>

}