package com.ylt.medic.rest.responses

import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName

/**
 * Created by yoannlt on 13/06/2017.
 */
data class MedicamentResponse (
    @SerializedName("code_cis") var codeCis: String = "",
    @SerializedName("date_amm") var dateAmm: String = "",
    @SerializedName("denomination") var denomination: String = "",
    @SerializedName("etat_commer") var etatCommer: String = "",
    @SerializedName("forme_pharma") var formePharma: String = "",
    @SerializedName("num_autor_eu") var numAutorEu:String = "",
    @SerializedName("status_bdm") var statusBdm: String = "",
    @SerializedName("statut_admin_amm") var statutAdminAmm: String = "",
    @SerializedName("surv_renforce")  var survRenforcee: String = "",
    @SerializedName("titulaire") var titulaire: String = "",
    @SerializedName("type_proced_amm") var typeProcedAmm: String = "",
    @SerializedName("voie_administration") var voieAdministration: String = "",

    @SerializedName("presentations") var presentations: List<PresentationResponse> = emptyList(),
    @SerializedName("compositions") var compos: List<CompoResponse> = emptyList(),
    @SerializedName("conditionsDelivrance") var conditionPrescritions: List<ConditionPrescriptionResponse> = emptyList(),
    @SerializedName("generiques") var generiques: List<GeneriqueResponse> = emptyList(),
    @SerializedName("infoImportantes") var infos: List<InfoImportantesResponse> = emptyList(),
    @SerializedName("smr") var SMRs: List<SmrResponse> = emptyList(),
    @SerializedName("asmr") var ASMRs: List<AsmrResponse> = emptyList()
)