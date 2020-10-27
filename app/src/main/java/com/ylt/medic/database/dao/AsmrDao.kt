package com.ylt.medic.database.dao

import androidx.room.*
import com.ylt.medic.database.model.ASMR
import com.ylt.medic.database.model.Medicament

@Dao
interface AsmrDao {

    @Query("SELECT * FROM asmr")
    fun getAllAsmr(): List<ASMR>

    @Query("SELECT * FROM asmr WHERE code_cis = :codeCis")
    fun getAsmrByCis(codeCis: String): List<ASMR>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg asmr: ASMR)

    @Update
    fun update(asmr: ASMR)

    @Delete
    fun delete(asmr: ASMR)

    @Query("DELETE FROM asmr")
    fun deleteTable()
}