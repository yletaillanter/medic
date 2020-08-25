package com.ylt.medic.database.dao

import androidx.room.*
import com.ylt.medic.database.model.InfoImportantes

@Dao
interface InfoImportantDao {

    @Query("SELECT * FROM info_importantes")
    fun getInfoImportantes(): List<InfoImportantes>

    @Query("SELECT * FROM info_importantes WHERE code_cis = :codeCis")
    fun getInfoImportantesByCis(codeCis: String): List<InfoImportantes>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg infoImportantes: InfoImportantes)

    @Delete
    fun delete(infoImportantes: InfoImportantes)

    @Query("DELETE FROM info_importantes")
    fun deleteTable()
}