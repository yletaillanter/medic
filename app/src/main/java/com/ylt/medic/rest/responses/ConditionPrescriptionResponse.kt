package com.ylt.medic.rest.responses

import com.google.gson.annotations.SerializedName

data class ConditionPrescriptionResponse (
    @SerializedName("code_cis") var codeCis: String = "",
    @SerializedName("condition") var condition: String = ""
)