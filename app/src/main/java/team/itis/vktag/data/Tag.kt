package team.itis.vktag.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.NotNull

class Tag {

    @SerializedName("id")
    @Expose
    var id: Int? = 0
    @SerializedName("admin_vk_user_id")
    @Expose
    var adminVkUserId: Int? = 0
    @SerializedName("hash")
    @Expose
    var hash: String? = null
    @SerializedName("type")
    @Expose
    @NotNull
    var type: String? = "ping"
    @SerializedName("data")
    @Expose
    var data: Any? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
}