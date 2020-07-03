package id.candraibra.bukuwarungtest.data.model


import com.google.gson.annotations.SerializedName

data class Ad(
    @SerializedName("company")
    var company: String,
    @SerializedName("text")
    var text: String,
    @SerializedName("url")
    var url: String
)