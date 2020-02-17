package com.example.edgemedicaloficial.Dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.edgemedicaloficial.Model.mRegistro.Registro;
import com.example.edgemedicaloficial.Model.mRegistro.User;

@Database(entities = {
        Registro.class,
        User.class,
}, version = 3, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    public abstract registroDao getRegistroDao();
    public abstract userDao getUser();
}