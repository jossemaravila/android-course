package br.com.caelum.alunos;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListaAlunosActivity extends Activity {

    public static final String ALUNO_SELECIONADO = "ALUNO_SELECIONADO";

    private List<Aluno> alunos;

    private int posicaoSelecionada;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista);

        ListView listaAlunos = (ListView) findViewById(R.id.listaAlunos);
        listaAlunos.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View view, int posicao, long id) {
                Aluno aluno = alunos.get(posicao);
                Intent intencao = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                intencao.putExtra(ALUNO_SELECIONADO, aluno);
                startActivity(intencao);
            }

        });

        listaAlunos.setOnItemLongClickListener(new OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int posicao, long id) {
                // Toast.makeText(ListaAlunosActivity.this, alunos.get(posicao),
                // Toast.LENGTH_LONG).show();
                posicaoSelecionada = posicao;
                return false;
            }
        });

        registerForContextMenu(listaAlunos);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAlunosListView();
    }

    private List<Aluno> loadAlunosFromDatabase() {
        AlunoDao dao = new AlunoDao(this);
        List<Aluno> alunos = dao.listar();
        dao.close();

        return alunos;
    }

    private void loadAlunosListView() {
        alunos = loadAlunosFromDatabase();
        ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunos);
        ListView listaAlunos = (ListView) findViewById(R.id.listaAlunos);
        listaAlunos.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_aluno, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        final Aluno alunoSelecionado = alunos.get(posicaoSelecionada);
        
        MenuItem menuExcluir = menu.add(0, 0, 0, "Excluir " + alunoSelecionado.getNome());
        menuExcluir.setOnMenuItemClickListener(new OnMenuItemClickListener(){
            public boolean onMenuItemClick(MenuItem item) {
                AlunoDao dao = new AlunoDao(ListaAlunosActivity.this);
                dao.excluir(alunoSelecionado);
                dao.close();
                
                loadAlunosListView();

                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
        case R.id.menu_novo:
            Intent intencao = new Intent(this, FormularioActivity.class);
            startActivity(intencao);
            break;
        }
        return super.onOptionsItemSelected(item);
    }
}