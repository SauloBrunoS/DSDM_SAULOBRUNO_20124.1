package com.example.roomdatabaseexemplo.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.roomdatabaseexemplo.Dao.PessoaDao;
import com.example.roomdatabaseexemplo.model.Pessoa;

@Database(entities = {Pessoa.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PessoaDao pessoaDao();
}