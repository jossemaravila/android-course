package br.com.caelum.alunos;

import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {

    private List<Aluno> alunos;
    private Activity activity;

    public ListViewAdapter(List<Aluno> alunos, Activity activity) {
        super();
        this.alunos = alunos;
        this.activity = activity;
    }

    public int getCount() {
        return alunos.size();
    }

    public Object getItem(int posicao) {
        return alunos.get(posicao);
    }

    public long getItemId(int posicao) {
        return ((Aluno) getItem(posicao)).getId();
    }

    public View getView(int posicao, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();

        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.lista_aluno, null);
        ImageView imageView = (ImageView) linearLayout.findViewById(R.id.foto_aluno);
        TextView textView = (TextView) linearLayout.findViewById(R.id.nome_aluno);

        Aluno aluno = (Aluno) getItem(posicao);
        textView.setText(aluno.getNome());
        carregaImagem(imageView, aluno.getImage());

        return linearLayout;
    }

    private void carregaImagem(ImageView imageView, String arquivo) {
        Bitmap bm = BitmapFactory.decodeFile(arquivo);
        if (bm != null) {
            bm = Bitmap.createScaledBitmap(bm, 100, 100, true);
        } else {
            bm = BitmapFactory.decodeResource(activity.getResources(), R.drawable.novo);
        }
        imageView.setImageBitmap(bm);
    }

}
