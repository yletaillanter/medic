package com.ylt.medic.rest.responses

import com.google.gson.annotations.SerializedName

data class InfoImportantesResponse (
    @SerializedName("code_cis") var codeCis: String = "",
    @SerializedName("date_deb_info") var dateDeb: String = "",
    @SerializedName("date_fin_info") var dateFin: String = "",
    @SerializedName("text_et_lien") var textAndLink: String = ""
)