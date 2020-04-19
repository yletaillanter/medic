package com.ylt.medic.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "smr")
data class SMR (
    @PrimaryKey(autoGenerate = true) var id: Long = 0L,
    @Ignore var medicament: Medicament = Medicament(),

    @ColumnInfo(name = "code_cis") var codeCis: String = "",
    @ColumnInfo(name = "code_dossier_has") var codeDossierHas: String = "",
    @ColumnInfo(name = "motif_eval") var motifEval: String = "",
    @ColumnInfo(name = "date_avis_comm_transp") var dateAvisCommTransp: String = "",
    @ColumnInfo(name = "valeur_asmr") var valeurSmr: String = "",
    @ColumnInfo(name = "libelle_asmr") var libelleSmr: String = ""
) {
    override fun toString(): String {
        return "SMR(id=$id, codeCis='$codeCis', codeDossierHas='$codeDossierHas', motifEval='$motifEval', dateAvisCommTransp='$dateAvisCommTransp', valeurSmr='$valeurSmr', libelleSmr='$libelleSmr')"
    }
}