package team.itis.vktag.data

import retrofit2.Call
import retrofit2.http.*


interface TagVKApi {

    @GET("/")
    fun getAdminTags(): Call<ApiResponse>

    @GET("/")
    fun getTag(@Query("hash") hash: String): Call<ApiResponse>

    @POST("/")
    fun updateTag(@Query("hash") hash: String,
                  @Query("title") title: String,
                  @Query("type") type: String,
                  @Query("data") data: Any,
                  @Query("allow_modify") allow_modify: Boolean = false): Call<ApiResponse>


    @PUT("/")
    fun insertTag(@Query("hash") hash: String,
                  @Query("title") title: String = "",
                  @Query("type") type: String,
                  @Query("data") data: Any,
                  @Query("allow_modify") allow_modify: Boolean = false): Call<ApiResponse>

    @DELETE("/")
    fun deleteTag(@Query("hash") hash: String): Call<ApiResponse>

}