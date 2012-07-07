package br.com.caelum.alunos;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListaAlunosActivity extends Activity {

    private List<Aluno> alunos;

    private int posicaoSelecionada;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista);

        ListView listaAlunos = (ListView) findViewById(R.id.listaAlunos);
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

        AlunoDao dao = new AlunoDao(this);
        alunos = dao.listar();
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

        menu.add(0, 0, 0, "Ligar para " + alunos.get(posicaoSelecionada));
        menu.add(0, 1, 0, "Enviar SMS");
        menu.add(0, 2, 0, "Achar no mapa");
        menu.add(0, 3, 0, "Navegar no site");
        menu.add(0, 4, 0, "Deletar");
        menu.add(0, 5, 0, "Enviar E-mail");
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