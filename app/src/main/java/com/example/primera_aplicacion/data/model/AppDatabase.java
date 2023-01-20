package com.example.primera_aplicacion.data.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.primera_aplicacion.data.dao.loginDao;

@Database(entities = Login.class, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    //Creamos un método por cada uno de los Daos que tenemos.
    public abstract loginDao loginDao();
}
