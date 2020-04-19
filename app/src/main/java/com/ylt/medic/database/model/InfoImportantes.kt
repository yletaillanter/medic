package com.ylt.medic.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "info_importantes")
data class InfoImportantes (
    @PrimaryKey(autoGenerate = true) var id: Long = 0L,
    @Ignore val medicament: Medicament = Medicament(),

    @ColumnInfo(name = "code_cis") var codeCis: String = "",
    @ColumnInfo(name = "date_deb_info") var dateDeb: String = "",
    @ColumnInfo(name = "date_fin_info") var dateFin: String = "",
    @ColumnInfo(name = "text_et_lien") var textAndLink: String = ""
) {
    override fun toString(): String {
        return "InfoImportantes(id=$id, codeCis='$codeCis', dateDeb=$dateDeb, dateFin=$dateFin, textAndLink='$textAndLink')"
    }
}