package br.com.caelum.alunos;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;

public class FormularioAlunoHelper {

    private static final String ATUALIZAR = "ATUALIZAR";
    private static final String GRAVAR = "GRAVAR";

    private FormularioActivity activity;

    private EditText nome;
    private EditText endereco;
    private EditText telefone;
    private EditText site;
    private RatingBar rating;
    private ImageButton image;
    private Button buttonInserir;

    private Long id;

    private String acao;

    public FormularioAlunoHelper(FormularioActivity formularioActivity) {
        activity = formularioActivity;

        this.inicializarReferenciasDosCampos();

        buttonInserir.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (acao.equalsIgnoreCase(GRAVAR)) {
                    gravarAluno();
                }
                if (acao.equalsIgnoreCase(ATUALIZAR)) {
                    atualizarAluno();
                }

                activity.finish();
            }

        });
    }

    private void inicializarReferenciasDosCampos() {
        nome = (EditText) findViewById(R.id.editNome);
        endereco = (EditText) findViewById(R.id.editEndereco);
        telefone = (EditText) findViewById(R.id.editTelefone);
        site = (EditText) findViewById(R.id.editSite);
        rating = (RatingBar) findViewById(R.id.ratingBar);
        image = (ImageButton) findViewById(R.id.imageAluno);
        buttonInserir = (Button) findViewById(R.id.buttonInserir);
        id = null;
    }

    public void inicializaFormulario(Aluno aluno) {
        if (aluno != null) {
            nome.setText(aluno.getNome());
            endereco.setText(aluno.getEndereco());
            telefone.setText(aluno.getTelefone());
            site.setText(aluno.getSite());
            rating.setRating(aluno.getRating());
            buttonInserir.setText(ATUALIZAR);

            id = aluno.getId();

            acao = ATUALIZAR;
        } else {
            nome.setText("");
            endereco.setText("");
            telefone.setText("");
            site.setText("");
            rating.setRating(0);
            buttonInserir.setText(GRAVAR);

            id = null;

            acao = GRAVAR;
        }
    }

    private void gravarAluno() {
        AlunoDao dao = new AlunoDao(activity);
        dao.insere(converterFormularioToAluno());
        dao.close();
    }

    private void atualizarAluno() {
        AlunoDao dao = new AlunoDao(activity);
        dao.atualizar(converterFormularioToAluno());
        dao.close();
    }

    private Aluno converterFormularioToAluno() {
        Aluno aluno = new Aluno();
        aluno.setId(id);
        aluno.setNome(nome.getText().toString());
        aluno.setEndereco(endereco.getText().toString());
        aluno.setTelefone(telefone.getText().toString());
        aluno.setSite(site.getText().toString());
        aluno.setRating(rating.getRating());
        aluno.setImage("imagem");
        return aluno;
    }

    private View findViewById(int id) {
        return activity.findViewById(id);
    }

}
