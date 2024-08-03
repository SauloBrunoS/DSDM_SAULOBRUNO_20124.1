package com.example.roomdatabaseexemplo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.roomdatabaseexemplo.Adapter.PessoaArrayAdapter;
import com.example.roomdatabaseexemplo.Dao.PessoaDao;
import com.example.roomdatabaseexemplo.Database.AppDatabase;
import com.example.roomdatabaseexemplo.model.Pessoa;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv_pessoas;

    EditText et_nome, et_curso, et_idade;

    FloatingActionButton fab_add;

    PessoaArrayAdapter pessoaArrayAdapter;

    PessoaDao pessoaDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv_pessoas = findViewById(R.id.rv_pessoas);
        et_nome = findViewById(R.id.et_nome);
        et_curso = findViewById(R.id.et_curso);
        et_idade = findViewById(R.id.et_idade);
        fab_add = findViewById(R.id.fab_add);

        AppDatabase appDatabase = Room.databaseBuilder(this,
                        AppDatabase.class,
                        "db_pessoas_2")
                .enableMultiInstanceInvalidation()
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        pessoaDao = appDatabase.pessoaDao();

        List<Pessoa> pessoasDoBd = pessoaDao.getAllPessoas();
        ArrayList<Pessoa> pessoas = new ArrayList<>(pessoasDoBd);
        pessoaArrayAdapter = new PessoaArrayAdapter(R.layout.pessoa_layout, pessoas);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        rv_pessoas.setLayoutManager(layoutManager);

        rv_pessoas.setAdapter(pessoaArrayAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                ((LinearLayoutManager) layoutManager).getOrientation());

        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.custom_divider);
        if (dividerDrawable != null) {
            dividerItemDecoration.setDrawable(dividerDrawable);
        }

        rv_pessoas.addItemDecoration(dividerItemDecoration);

        swipeToDelte();

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String nome = et_nome.getText().toString();
                    String curso = et_curso.getText().toString();
                    if(et_idade.getText().toString().isEmpty()){
                        Toast.makeText(MainActivity.this, "Insira idade!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int idade = Integer.parseInt(et_idade.getText().toString());

                    if (nome.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Insira nome!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (curso.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Insira curso!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Pessoa novaPessoa = new Pessoa(nome, curso, idade);

                    pessoaDao.insertAll(novaPessoa);

                    pessoaArrayAdapter.addPessoa(novaPessoa);

                    Toast.makeText(MainActivity.this, novaPessoa.getNome() + " adicionado com sucesso!", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Idade inválida! Por favor, insira um número.", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Ocorreu um erro ao adicionar a pessoa!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

    }

    private void swipeToDelte() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                try {
                    int position = viewHolder.getAdapterPosition();
                    Pessoa pessoaToDelete = pessoaArrayAdapter.getPessoaPorPosicao(position);
                    pessoaDao.deleteById(pessoaToDelete.getUid());
                    pessoaArrayAdapter.deletePessoa(pessoaToDelete);
                    Toast.makeText(MainActivity.this, pessoaToDelete.getNome() + " removido com sucesso!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Ocorreu um erro ao remover a pessoa!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        itemTouchHelper.attachToRecyclerView(rv_pessoas);
    }
}