package com.ylt.medic.rest.responses

import com.google.gson.annotations.SerializedName


data class GeneriqueResponse (
    @SerializedName( "code_cis") var codeCis: String = "",
    @SerializedName( "id_grp_gener") var idGrpGener:String = "",
    @SerializedName( "libelle_grp_gener") var libelleGrpGener: String = "",
    @SerializedName( "type_gener") var typeGener: String = "",
    @SerializedName( "numero_tri") var numeroTri: String = ""
)