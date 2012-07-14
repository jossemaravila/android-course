package br.com.caelum.alunos;

import android.app.Activity;
import android.os.Bundle;

public class FormularioActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario);

        FormularioAlunoHelper helper = new FormularioAlunoHelper(this);

        Aluno aluno = (Aluno) getIntent().getSerializableExtra(ListaAlunosActivity.ALUNO_SELECIONADO);
        helper.inicializaFormulario(aluno);
    }

    public void onBackPressed() {
        finish();
    }

}