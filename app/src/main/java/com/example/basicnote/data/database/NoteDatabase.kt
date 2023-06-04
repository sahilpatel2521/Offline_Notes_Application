package com.example.basicnote.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.basicnote.data.dao.NoteDao
import com.example.basicnote.data.entity.Note

@Database(entities = arrayOf(Note::class), version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao() : NoteDao

}