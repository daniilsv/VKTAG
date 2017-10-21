package team.itis.vktag.data

import retrofit2.Call
import retrofit2.http.*


interface TegVKApi {

    @GET("teg_VK.item")
    fun getInventoryItem(@Query("name") name: String): Call<Teg>

    @POST("teg_VK.item")
    fun updateInventoryItem(@Query("name") name: String,
                            @Query("item[title]") title: String,
                            @Query("item[href]") href: String,
                            @Query("item[description]") description: String): Call<Teg>


    //update == save for update item
    @PUT("teg_VK.item")
    fun insertInventoryItem(@Query("name") name: String,
                            @Query("item[title]") title: String,
                            @Query("item[href]") href: String,
                            @Query("item[description]") description: String): Call<Teg>
//insert == save for new items

    @DELETE("teg_VK.item")
    fun deleteInventoryItem(@Query("name") name: String): Call<Teg>

}