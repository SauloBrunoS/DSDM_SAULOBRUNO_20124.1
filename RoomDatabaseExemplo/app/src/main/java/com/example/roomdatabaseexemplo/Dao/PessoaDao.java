package com.example.roomdatabaseexemplo.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.roomdatabaseexemplo.model.Pessoa;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface PessoaDao {
    @Query("SELECT * FROM pessoa")
    List<Pessoa> getAllPessoas();

    @Insert
    void insertAll(Pessoa...pessoas);

    @Delete
    void delete(Pessoa pessoa);
}