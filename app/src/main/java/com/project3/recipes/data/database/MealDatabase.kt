package com.project3.recipes.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.project3.recipes.data.model.Meal

@Database(entities = [Meal::class], version = 1, exportSchema = false)
abstract class MealDatabase : RoomDatabase() {
    abstract fun mealDao(): MealDao

    companion object {
        private var mealDatabase: MealDatabase? = null

        fun getInstance(context: Context): MealDatabase {
            return mealDatabase ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MealDatabase::class.java,
                    "dog_image_database"
                ).build()
                mealDatabase = instance
                instance
            }
        }
    }
}