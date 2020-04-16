package com.ylt.medic.database.model

import androidx.room.*

@Entity(tableName = "composition")
data class Compo (
    @PrimaryKey(autoGenerate = true) var id: Long = 0L,
    @Ignore val medicament: Medicament = Medicament(),

    @ColumnInfo(name = "code_cis") var codeCis: String = "",
    @ColumnInfo(name = "designation_elem_ph") var designationElemPh: String = "",
    @ColumnInfo(name = "code_substance") var codeSubstance: String = "",
    @ColumnInfo(name = "deno_substance") var denoSubstance: String = "",
    @ColumnInfo(name = "dosage_substance") var dosageSubstance: String = "",
    @ColumnInfo(name = "ref_substance") var refSubstance: String = "",
    @ColumnInfo(name = "nature_composant") var natureComposant: String = "",
    @ColumnInfo(name = "num_liaison_sa_ft") var numLiaisonSaFt: String = ""
)