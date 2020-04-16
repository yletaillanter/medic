package com.ylt.medic.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ylt.medic.database.model.ASMR

interface AsmrDao {

    @Query("SELECT * FROM asmr")
    fun getAllAsmr(): List<ASMR>

    @Query("SELECT * FROM asmr WHERE code_cis = :codeCis")
    fun getAsmrByCis(codeCis: String): List<ASMR>

    @Insert
    fun insert(vararg medic: ASMR): List<Long>

    @Delete
    fun delete(medic: ASMR)
}