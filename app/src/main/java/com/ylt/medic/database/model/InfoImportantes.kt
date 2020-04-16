package com.ylt.medic.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "info_importantes")
data class InfoImportantes (
    @PrimaryKey(autoGenerate = true) var id: Long = 0L,
    @Ignore val medicament: Medicament = Medicament(),

    @ColumnInfo(name = "code_cis") var codeCis: String = "",
    @ColumnInfo(name = "date_deb_info") var dateDeb: Date = Date(),
    @ColumnInfo(name = "date_fin_info") var dateFin: Date= Date(),
    @ColumnInfo(name = "text_et_lien") var textAndLink: String = ""
)