package br.com.caelum.alunos;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

public class EnviarAlunosTask extends AsyncTask<Object, Object, String> {

    private Activity context;
    private List<Aluno> alunos;
    private ProgressDialog progress;

    public EnviarAlunosTask(Activity context, List<Aluno> alunos) {
        super();
        this.context = context;
        this.alunos = alunos;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        progress = ProgressDialog.show(context, "Sincronizar Alunos", "Aguarde...");
    }

    protected String doInBackground(Object... params) {
        WebClient wc = new WebClient("http://www.caelum.com.br/mobile/");
        String resultado = null;
        try {
            resultado = wc.post(new AlunoConverter(alunos).toJson());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        progress.dismiss();
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }

}
