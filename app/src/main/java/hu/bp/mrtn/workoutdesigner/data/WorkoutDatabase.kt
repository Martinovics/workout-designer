package hu.bp.mrtn.workoutdesigner.data

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase




@Database(
    entities = [ExerciseModel::class], version = 1,
    //autoMigrations = [AutoMigration (from=1, to=2, spec=CustomMigration::class)]
)
abstract class WorkoutDatabase : RoomDatabase() {


    abstract fun getDao(): ExerciseModelDao


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
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}