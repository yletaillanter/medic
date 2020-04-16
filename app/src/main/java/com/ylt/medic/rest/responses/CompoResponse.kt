package com.ylt.medic.rest.responses

import com.google.gson.annotations.SerializedName

data class CompoResponse (
    @SerializedName( "code_cis") var codeCis: String = "",
    @SerializedName( "designation_elem_ph") var designationElemPh: String = "",
    @SerializedName( "code_substance") var codeSubstance: String = "",
    @SerializedName( "deno_substance") var denoSubstance: String = "",
    @SerializedName( "dosage_substance") var dosageSubstance: String = "",
    @SerializedName( "ref_substance") var refSubstance: String = "",
    @SerializedName( "nature_composant") var natureComposant: String = "",
    @SerializedName( "num_liaison_sa_ft") var numLiaisonSaFt: String = ""
)