package br.com.caelum.alunos;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AlunoDao extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "Aluno";
    private static final Integer VERSION_NUMBER = 1;
    private static final String DATABASE_NAME = "FJ57";

    public AlunoDao(Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUMBER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder ddl = new StringBuilder();
        ddl.append("CREATE TABLE ");
        ddl.append(TABLE_NAME);
        ddl.append(" (");
        ddl.append("    ID INTEGER PRIMARY KEY, ");
        ddl.append("    NOME TEXT UNIQUE NOT NULL, ");
        ddl.append("    ENDERECO TEXT, ");
        ddl.append("    TELEFONE TEXT, ");
        ddl.append("    SITE TEXT, ");
        ddl.append("    RATING INTEGER, ");
        ddl.append("    FOTO TEXT ");
        ddl.append(" );");

        db.execSQL(ddl.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        StringBuilder ddl = new StringBuilder();
        ddl.append("DROP TABLE ");
        ddl.append(TABLE_NAME);

        db.execSQL(ddl.toString());

        onCreate(db);
    }

    public void insere(Aluno aluno) {
        ContentValues values = new ContentValues();
        values.put("NOME", aluno.getNome());
        values.put("ENDERECO", aluno.getEndereco());
        values.put("TELEFONE", aluno.getTelefone());
        values.put("SITE", aluno.getSite());
        values.put("RATING", aluno.getRating());
        values.put("FOTO", aluno.getImage());

        getWritableDatabase().insert(TABLE_NAME, null, values);
    }

    public List<Aluno> listar() {
        List<Aluno> alunos = new ArrayList<Aluno>();

        String[] colunas = {"ID", "NOME", "ENDERECO", "TELEFONE", "SITE", "RATING", "FOTO"};
        Cursor c = getWritableDatabase().query(TABLE_NAME, colunas, null, null, null, null, null);

        while(c.moveToNext()){
            Aluno aluno = new Aluno();
            aluno.setId(c.getLong(0));
            aluno.setNome(c.getString(1));
            aluno.setEndereco(c.getString(2));
            aluno.setTelefone(c.getString(3));
            aluno.setSite(c.getString(4));
            aluno.setRating(c.getFloat(5));
            aluno.setImage(c.getString(6));
            
            alunos.add(aluno);
        }
        c.close();
        
        return alunos;
    }
}
