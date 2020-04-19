package com.ylt.medic.rest.responses

import com.google.gson.annotations.SerializedName

data class AsmrResponse (
    @SerializedName("code_cis") var codeCis: String = "",
    @SerializedName("code_dossier_has") var codeDossierHas: String = "",
    @SerializedName("motif_eval") var motifEval: String = "",
    @SerializedName("date_avis_comm_transp") var dateAvisCommTransp: String = "",
    @SerializedName("valeur_asmr") var valeurAsmr: String = "",
    @SerializedName("libelle_asmr") var libelleAsmr: String = ""
    //@SerializedName("liens_ct") var liensAvisCT: Array<String> = emptyArray()
)