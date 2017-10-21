package team.itis.vktag.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ApiResponse {
    @SerializedName("status")
    @Expose
    var status: String? = null
    @SerializedName("msg")
    @Expose
    var msg: String? = null
    @SerializedName("tags")
    @Expose
    var tags: List<Tag>? = null
    @SerializedName("tag")
    @Expose
    var tag: Tag? = null
}
