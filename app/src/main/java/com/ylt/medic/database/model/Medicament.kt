package com.ylt.medic.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by yoannlt on 13/06/2017.
 */
@Entity(tableName = "medicament")
data class Medicament (

    @PrimaryKey(autoGenerate = true) var id: Long = 0L,

    @ColumnInfo(name = "code_cis") var codeCis: String = "",
    @ColumnInfo(name = "date_amm") var dateAmm: String = "",
    @ColumnInfo(name = "denomination") var denomination: String = "",
    @ColumnInfo(name = "etat_commer") var etatCommer: String = "",
    @ColumnInfo(name = "forme_pharma") var formePharma: String = "",
    @ColumnInfo(name = "num_autor_eu") var numAutorEu:String = "",
    @ColumnInfo(name = "status_bdm") var statusBdm: String = "",
    @ColumnInfo(name = "statut_admin_amm") var statutAdminAmm: String = "",
    @ColumnInfo(name = "surv_renforcee") var survRenforcee: String = "",
    @ColumnInfo(name = "titulaire") var titulaire: String = "",
    @ColumnInfo(name = "type_proced_amm") var typeProcedAmm: String = "",
    @ColumnInfo(name = "voie_administration") var voieAdministration: String = "",

    var isBookmarked: Boolean = false,

    @Ignore var compos: List<Compo> = emptyList(),
    @Ignore var presentations: List<Presentation> = emptyList(),
    @Ignore var conditionPrescritions: List<ConditionPrescription> = emptyList(),
    @Ignore var generiques: List<Generique> = emptyList(),
    @Ignore var liensAvisCT: List<String> = emptyList(),
    @Ignore var infos: List<InfoImportantes> = emptyList(),
    @Ignore var SMRs: List<SMR> = emptyList(),
    @Ignore var ASMRs: List<ASMR> = emptyList()
)