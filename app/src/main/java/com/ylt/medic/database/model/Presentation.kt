package com.ylt.medic.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "presentation")
data class Presentation (
    @PrimaryKey(autoGenerate = true) var id: Long = 0L,
    @Ignore var medicament: Medicament = Medicament(),

    @ColumnInfo(name = "code_cis") var codeCis: String = "",
    @ColumnInfo(name = "code_cip7") var codeCip7: String = "",
    @ColumnInfo(name = "code_cip13") var codeCip13: String = "",
    @ColumnInfo(name = "libelle_presentation") var libellePresentation: String = "",
    @ColumnInfo(name = "statut_admin_pres") var statutAdminPres: String = "",
    @ColumnInfo(name = "etat_commer") var etatCommer: String = "",
    @ColumnInfo(name = "date_decla_commer") var dateDeclaCommer: String = "",
    @ColumnInfo(name = "agrement_collec") var agrementCollec: String = "",
    @ColumnInfo(name = "tx_remboursement") var txRemboursement: String = "",
    @ColumnInfo(name = "prix_medic_euro") var prixMedicEuro: String = "",
    @ColumnInfo(name = "indic_droit_remb") var indicDroitRemb: String = ""
)