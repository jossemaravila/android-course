package br.com.caelum.alunos;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;

public class FormularioActivity extends Activity {

    private EditText nome;
    private EditText endereco;
    private EditText telefone;
    private EditText site;
    private RatingBar rating;
    private ImageButton image;
    private Button buttonInserir;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario);

        nome = (EditText) findViewById(R.id.editNome);
        endereco = (EditText) findViewById(R.id.editEndereco);
        telefone = (EditText) findViewById(R.id.editTelefone);
        site = (EditText) findViewById(R.id.editSite);
        rating = (RatingBar) findViewById(R.id.ratingBar);
        image = (ImageButton) findViewById(R.id.imageAluno);

        buttonInserir = (Button) findViewById(R.id.buttonInserir);
        buttonInserir.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Aluno aluno = new Aluno();
                aluno.setNome(nome.getText().toString());
                aluno.setEndereco(endereco.getText().toString());
                aluno.setTelefone(telefone.getText().toString());
                aluno.setSite(site.getText().toString());
                aluno.setRating(rating.getRating());
                aluno.setImage("imagem");

                AlunoDao dao = new AlunoDao(FormularioActivity.this);
                dao.insere(aluno);
                dao.close();
                
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}