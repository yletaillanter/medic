package com.ylt.medic.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "asmr")
data class ASMR (
    @PrimaryKey(autoGenerate = true) var id: Long = 0L,
    @Ignore var medicament: Medicament = Medicament(),

    @ColumnInfo(name = "code_cis") var codeCis: String = "",
    @ColumnInfo(name = "code_dossier_has") var codeDossierHas: String = "",
    @ColumnInfo(name = "motif_eval") var motifEval: String = "",
    @ColumnInfo(name = "date_avis_comm_transp") var dateAvisCommTransp: String = "",
    @ColumnInfo(name = "valeur_asmr") var valeurAsmr: String = "",
    @ColumnInfo(name = "libelle_asmr") var libelleAsmr: String = ""
)