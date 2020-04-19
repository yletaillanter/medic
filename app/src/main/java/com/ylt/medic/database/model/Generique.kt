package com.ylt.medic.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


@Entity(tableName = "generique")
data class Generique (
    @PrimaryKey(autoGenerate = true) var id: Long = 0L,
    @Ignore val medicament: Medicament = Medicament(),

    @ColumnInfo(name = "code_cis") var codeCis: String = "",
    @ColumnInfo(name = "id_grp_gener") var idGrpGener:String = "",
    @ColumnInfo(name = "libelle_grp_gener") var libelleGrpGener: String = "",
    @ColumnInfo(name = "type_gener") var typeGener: String = "",
    @ColumnInfo(name = "numero_tri") var numeroTri: String = ""
) {
    override fun toString(): String {
        return "Generique(id=$id, codeCis='$codeCis', idGrpGener='$idGrpGener', libelleGrpGener='$libelleGrpGener', typeGener='$typeGener', numeroTri='$numeroTri')"
    }
}