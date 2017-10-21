package team.itis.vktag.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Teg {

    @SerializedName("hash")
    @Expose
    var hash: String? = null
    @SerializedName("user_id")
    @Expose
    var user_id: String? = null

    fun getMap(): HashMap<String, String> {
        val map: HashMap<String, String> = HashMap()
        map["hash"] = hash!!
        map["user_id"] = user_id!!
        return map
    }
}