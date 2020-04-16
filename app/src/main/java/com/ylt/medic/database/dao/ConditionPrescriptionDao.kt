package com.ylt.medic.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ylt.medic.database.model.ConditionPrescription

interface ConditionPrescriptionDao {

    @Query("SELECT * FROM condition_prescription")
    fun getAllConditionPrescription(): List<ConditionPrescription>

    @Query("SELECT * FROM condition_prescription WHERE code_cis = :codeCis")
    fun getConditionPrescriptionByCis(codeCis: String): List<ConditionPrescription>

    @Insert
    fun insert(vararg medic: ConditionPrescription): List<Long>

    @Delete
    fun delete(medic: ConditionPrescription)
}