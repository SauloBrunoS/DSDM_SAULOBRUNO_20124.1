package com.example.roomdatabaseexemplo.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pessoa")
public class Pessoa {
        @PrimaryKey(autoGenerate = true)
        private int uid;
        @ColumnInfo(name = "nome")
        private String nome;
        @ColumnInfo(name = "curso")
        private String curso;
        @ColumnInfo(name = "idade")
        private int idade;

        public Pessoa (String nome, String curso, int idade){
           this.nome = nome;
           this.curso = curso;
           this.idade = idade;
        }

        public int getUid() {
                return uid;
        }

        public void setUid(int uid) {
                this.uid = uid;
        }

        public String getNome() {
                return nome;
        }

        public void setNome(String nome) {
                this.nome = nome;
        }

        public String getCurso() {
                return curso;
        }

        public void setCurso(String curso) {
                this.curso = curso;
        }

        public int getIdade() {
                return idade;
        }

        public void setIdade(int idade) {
                this.idade = idade;
        }

        @NonNull
        @Override
        public String toString() {
                String nomeRetorno = this.uid + " | " + this.nome + " | " + this.curso + " | " + this.idade;
                return nomeRetorno ;
        }
}
