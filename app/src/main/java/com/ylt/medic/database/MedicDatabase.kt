package com.ylt.medic.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.TypeConverters
import com.ylt.medic.database.dao.AsmrDao
import com.ylt.medic.database.dao.CompoDao
import com.ylt.medic.database.dao.MedicamentDao
import com.ylt.medic.database.model.*

/**
 * Created by yoannlt on 14/06/2017.
 */
@Database(version = 6, exportSchema = false,
    entities =
        arrayOf(
            ASMR::class,
            Compo::class,
            ConditionPrescription::class,
            Generique::class,
            InfoImportantes::class,
            Medicament::class,
            Presentation::class,
            SMR::class))
@TypeConverters(ConverterDate::class)
abstract class MedicDatabase : RoomDatabase() {
    abstract fun medicamentDao(): MedicamentDao
    abstract fun compoDao(): CompoDao

    companion object {
        private var instance: MedicDatabase? = null
        fun getInstance(context: Context): MedicDatabase {
            var instance = instance
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext, MedicDatabase::class.java, "medicamentdb")
                        .allowMainThreadQueries()
                        .build()
                this.instance = instance
            }
            return instance
        }

    }
}
