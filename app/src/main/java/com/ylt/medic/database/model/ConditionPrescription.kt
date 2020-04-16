package com.ylt.medic.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "condition_prescription")
data class ConditionPrescription (
    @PrimaryKey(autoGenerate = true) var id: Long = 0L,
    @Ignore var medicament: Medicament = Medicament(),

    @ColumnInfo(name = "code_cis") var codeCis: String = "",
    @ColumnInfo(name = "condition") var condition: String = ""
)