package id.candraibra.bukuwarungtest.data.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("ad")
    var ad: Ad,
    @SerializedName("data")
    var data: List<User>,
    @SerializedName("page")
    var page: Int,
    @SerializedName("per_page")
    var perPage: Int,
    @SerializedName("total")
    var total: Int,
    @SerializedName("total_pages")
    var totalPages: Int
)