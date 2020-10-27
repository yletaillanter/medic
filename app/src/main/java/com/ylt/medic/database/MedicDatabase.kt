package com.ylt.medic.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.TypeConverters
import com.ylt.medic.database.dao.*
import com.ylt.medic.database.model.*

/**
 * Created by yoannlt on 14/06/2017.
 */
@Database(entities = [ASMR::class, Compo::class, ConditionPrescription::class, Generique::class, InfoImportantes::class, Medicament::class, Presentation::class, SMR::class], version = 6, exportSchema = false)
@TypeConverters(ConverterDate::class)
abstract class MedicDatabase : RoomDatabase() {
    abstract fun asmrDao(): AsmrDao
    abstract fun compoDao(): CompoDao
    abstract fun conditionPrescriptionDao(): ConditionPrescriptionDao
    abstract fun generiqueDao(): GeneriqueDao
    abstract fun infoImportantDao(): InfoImportantDao
    abstract fun medicamentDao(): MedicamentDao
    abstract fun presentationDao(): PresentationDao
    abstract fun smrDao(): SmrDao

    companion object {
        @Volatile
        private var INSTANCE: MedicDatabase? = null


        fun getInstance(context: Context): MedicDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MedicDatabase::class.java,
                    "medicament-app"
                ).allowMainThreadQueries().fallbackToDestructiveMigration().build()

                INSTANCE = instance
                return instance
            }
        }
    }
}
