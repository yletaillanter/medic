package com.ylt.medic.rest.responses

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
    @SerializedName("surv_renforcee")  var survRenforcee: String = "",
    @SerializedName("titulaire") var titulaire: String = "",
    @SerializedName("type_proced_amm") var typeProcedAmm: String = "",
    @SerializedName("voie_administration") var voieAdministration: String = "",

    var isBookmarked: Boolean = false,

    @SerializedName("compositions") var compos: Array<CompoResponse> = emptyArray(),
    @SerializedName("presentations") var presentations: Array<PresentationResponse> = emptyArray(),
    @SerializedName("conditionsDelivrance") var conditionPrescritions: Array<ConditionPrescriptionResponse> = emptyArray(),
    @SerializedName("generiques") var generiques: Array<GeneriqueResponse> = emptyArray(),
    @SerializedName("infoImportantes") var infos: Array<InfoImportantesResponse> = emptyArray(),
    @SerializedName("smr") var SMRs: Array<SmrResponse> = emptyArray(),
    @SerializedName("asmr") var ASMRs: Array<AsmrResponse> = emptyArray()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MedicamentResponse

        if (codeCis != other.codeCis) return false
        if (dateAmm != other.dateAmm) return false
        if (denomination != other.denomination) return false
        if (etatCommer != other.etatCommer) return false
        if (formePharma != other.formePharma) return false
        if (numAutorEu != other.numAutorEu) return false
        if (statusBdm != other.statusBdm) return false
        if (statutAdminAmm != other.statutAdminAmm) return false
        if (survRenforcee != other.survRenforcee) return false
        if (titulaire != other.titulaire) return false
        if (typeProcedAmm != other.typeProcedAmm) return false
        if (voieAdministration != other.voieAdministration) return false
        if (isBookmarked != other.isBookmarked) return false
        if (!compos.contentEquals(other.compos)) return false
        if (!presentations.contentEquals(other.presentations)) return false
        if (!conditionPrescritions.contentEquals(other.conditionPrescritions)) return false
        if (!generiques.contentEquals(other.generiques)) return false
        if (!infos.contentEquals(other.infos)) return false
        if (!SMRs.contentEquals(other.SMRs)) return false
        if (!ASMRs.contentEquals(other.ASMRs)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = codeCis.hashCode()
        result = 31 * result + dateAmm.hashCode()
        result = 31 * result + denomination.hashCode()
        result = 31 * result + etatCommer.hashCode()
        result = 31 * result + formePharma.hashCode()
        result = 31 * result + numAutorEu.hashCode()
        result = 31 * result + statusBdm.hashCode()
        result = 31 * result + statutAdminAmm.hashCode()
        result = 31 * result + survRenforcee.hashCode()
        result = 31 * result + titulaire.hashCode()
        result = 31 * result + typeProcedAmm.hashCode()
        result = 31 * result + voieAdministration.hashCode()
        result = 31 * result + isBookmarked.hashCode()
        result = 31 * result + compos.contentHashCode()
        result = 31 * result + presentations.contentHashCode()
        result = 31 * result + conditionPrescritions.contentHashCode()
        result = 31 * result + generiques.contentHashCode()
        result = 31 * result + infos.contentHashCode()
        result = 31 * result + SMRs.contentHashCode()
        result = 31 * result + ASMRs.contentHashCode()
        return result
    }
}