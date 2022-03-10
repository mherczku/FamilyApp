package hu.hm.familyapp.data.local

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DiskModule {

    companion object {
        private const val DB_NAME = "family-db"
    }

    @Provides
    @Singleton
    fun provideFamilyDao(familyDatabase: FamilyDatabase): FamilyDao = familyDatabase.familyDao()

    @Provides
    @Singleton
    fun provideNyTimesDatabase(@ApplicationContext context: Context): FamilyDatabase {
        return Room.databaseBuilder(context, FamilyDatabase::class.java, DB_NAME).build()
    }

}