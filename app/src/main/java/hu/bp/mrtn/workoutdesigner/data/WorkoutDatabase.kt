package hu.bp.mrtn.workoutdesigner.data

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase




@Database(
    entities = [WorkoutModel::class, ExerciseModel::class], version = 5,
    autoMigrations = [AutoMigration (from=4, to=5, spec=CustomMigration::class)]
)
abstract class WorkoutDatabase : RoomDatabase() {


    abstract fun getDao(): DatabaseDao


    companion object {

        @Volatile
        private var INSTANCE: WorkoutDatabase? = null

        fun getDatabase(applicationContext: Context): WorkoutDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    applicationContext,
                    WorkoutDatabase::class.java,
                    "workout-planner"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}