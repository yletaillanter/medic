package com.ylt.medic.database.dao

import androidx.room.*
import com.ylt.medic.database.model.ConditionPrescription

@Dao
interface ConditionPrescriptionDao {

    @Query("SELECT * FROM condition_prescription")
    fun getAllConditionPrescription(): List<ConditionPrescription>

    @Query("SELECT * FROM condition_prescription WHERE code_cis = :codeCis")
    fun getConditionPrescriptionByCis(codeCis: String): List<ConditionPrescription>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg conditionPrescritption: ConditionPrescription): List<Long>

    @Delete
    fun delete(conditionPrescritption: ConditionPrescription)

    @Query("DELETE FROM condition_prescription")
    fun deleteTable()
}