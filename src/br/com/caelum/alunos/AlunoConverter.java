package br.com.caelum.alunos;

import java.util.List;

import org.json.JSONException;
import org.json.JSONStringer;

public class AlunoConverter {

    private List<Aluno> alunos;

    public AlunoConverter(List<Aluno> alunos) {
        super();
        this.alunos = alunos;
    }
    
    public String toJson() throws JSONException{
        JSONStringer js = new JSONStringer();
        js.object().key("list").array().object().key("aluno").array();

        for (Aluno aluno : alunos) {
            js.object().key("id").value(aluno.getId())
                .key("nome").value(aluno.getNome())
                .key("telefone").value(aluno.getTelefone())
                .key("site").value(aluno.getSite())
                .key("foto").value(aluno.getImage());
        }
        
        js.endArray().endObject().endArray().endObject();
        
        return js.toString(); 
    }
    
    
}
