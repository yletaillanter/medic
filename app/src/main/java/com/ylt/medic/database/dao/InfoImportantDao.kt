package com.ylt.medic.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ylt.medic.database.model.InfoImportantes

interface InfoImportantDao {
    @Query("SELECT * FROM info_importantes")
    fun getInfoImportantes(): List<InfoImportantes>

    @Query("SELECT * FROM info_importantes WHERE code_cis = :codeCis")
    fun getInfoImportantesByCis(codeCis: String): List<InfoImportantes>

    @Insert
    fun insert(vararg medic: InfoImportantes): List<Long>

    @Delete
    fun delete(medic: InfoImportantes)
}