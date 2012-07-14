package br.com.caelum.alunos;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;

public class FormularioActivity extends Activity {

    private static final String ATUALIZAR = "ATUALIZAR";
    private static final String GRAVAR = "GRAVAR";
    private static final int CAPTURE_ALUNO_IMAGE = 1;

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

    private String arquivo;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario);

        this.inicializarReferenciasDosCampos();

        image.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                arquivo = Environment.getExternalStorageDirectory() + "/" + System.currentTimeMillis() + ".jpg";

                File file = new File(arquivo);
                Uri uri = Uri.fromFile(file);

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                startActivityForResult(intent, CAPTURE_ALUNO_IMAGE);
            }
        });

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

        Aluno aluno = (Aluno) getIntent().getSerializableExtra(ListaAlunosActivity.ALUNO_SELECIONADO);
        this.inicializaFormulario(aluno);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_ALUNO_IMAGE) {
            if (resultCode == RESULT_OK) {
                carregaImagem();
            }
        }
    }

    private void carregaImagem() {
        Bitmap bm;
        if (arquivo != null) {
            bm = BitmapFactory.decodeFile(arquivo);
            if (bm != null) {
                bm = Bitmap.createScaledBitmap(bm, 100, 100, true);
            } else {
                bm = BitmapFactory.decodeResource(getResources(), R.drawable.novo);
            }
        } else {
            bm = BitmapFactory.decodeResource(getResources(), R.drawable.novo);
        }
        image.setImageBitmap(bm);
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

    private void inicializaFormulario(Aluno aluno) {
        if (aluno != null) {
            nome.setText(aluno.getNome());
            endereco.setText(aluno.getEndereco());
            telefone.setText(aluno.getTelefone());
            site.setText(aluno.getSite());
            rating.setRating(aluno.getRating());
            buttonInserir.setText(ATUALIZAR);
            arquivo = aluno.getImage();
            carregaImagem();

            id = aluno.getId();

            acao = ATUALIZAR;
        } else {
            nome.setText("");
            endereco.setText("");
            telefone.setText("");
            site.setText("");
            rating.setRating(0);
            buttonInserir.setText(GRAVAR);
            carregaImagem();

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
        aluno.setImage(arquivo);

        return aluno;
    }

}