package com.ylt.medic.rest.responses

import com.google.gson.annotations.SerializedName

data class PresentationResponse (
    @SerializedName("code_cis") var codeCis: String = "",
    @SerializedName("code_cip7") var codeCip7: String = "",
    @SerializedName("code_cip13") var codeCip13: String = "",
    @SerializedName("libelle_presentation") var libellePresentation: String = "",
    @SerializedName("statut_admin_pres") var statutAdminPres: String = "",
    @SerializedName("etat_commer") var etatCommer: String = "",
    @SerializedName("date_decla_commer") var dateDeclaCommer: String = "",
    @SerializedName("agrement_collec") var agrementCollec: String = "",
    @SerializedName("tx_remboursement") var txRemboursement: String = "",
    @SerializedName("prix_medic_euro") var prixMedicEuro: String = "",
    @SerializedName("indic_droit_remb") var indicDroitRemb: String = ""
)