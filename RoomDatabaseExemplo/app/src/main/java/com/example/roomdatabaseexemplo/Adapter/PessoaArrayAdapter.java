package com.example.roomdatabaseexemplo.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomdatabaseexemplo.R;
import com.example.roomdatabaseexemplo.model.Pessoa;

import java.util.ArrayList;
import java.util.List;

public class PessoaArrayAdapter extends RecyclerView.Adapter<PessoaArrayAdapter.ViewHolder> {

    private int pessoaLayout;
    private ArrayList<Pessoa> pessoaList;

    public PessoaArrayAdapter(int layoutId, ArrayList<Pessoa> pessoaList){
        this.pessoaLayout = layoutId;
        this.pessoaList = pessoaList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(pessoaLayout, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TextView tv_nome = holder.tv_nome;
        TextView tv_curso = holder.tv_curso;
        TextView tv_idade = holder.tv_idade;
        tv_nome.setText(pessoaList.get(position).getNome());
        tv_curso.setText(pessoaList.get(position).getCurso());
        tv_idade.setText(String.valueOf(pessoaList.get(position).getIdade()));
    }

    @Override
    public int getItemCount() {
        return pessoaList == null ? 0 : pessoaList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_nome;
        public TextView tv_curso;
        public TextView tv_idade;

        public ViewHolder (View pessoaView){
            super(pessoaView);
            tv_nome = pessoaView.findViewById(R.id.tv_nome);
            tv_curso = pessoaView.findViewById(R.id.tv_curso);
            tv_idade = pessoaView.findViewById(R.id.tv_idade);
        }
    }

    public Pessoa getPessoaPorPosicao(int posicao){
        return pessoaList.get(posicao);
    }

    public void addPessoa(Pessoa pessoa) {
        this.pessoaList.add(pessoa);
        notifyDataSetChanged();
    }

    public void deletePessoa(Pessoa pessoa) {
        this.pessoaList.remove(pessoa);
        notifyDataSetChanged();
    }
}
