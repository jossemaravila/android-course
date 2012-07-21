package br.com.caelum.alunos;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class WebClient {

    private String url;

    public WebClient(String url) {
        super();
        this.url = url;
    }

    public String post(String json) throws ClientProtocolException, IOException {
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-type", "application/json");
        post.setHeader("Accept", "applicaton/json");
        post.setEntity(new StringEntity(json));

        HttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(post);
        String resultado = EntityUtils.toString(response.getEntity());

        return resultado;
    }
    
    public String get() throws ClientProtocolException, IOException {
        HttpGet get = new HttpGet(url);
        
        HttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(get);
        String resultado = EntityUtils.toString(response.getEntity());

        return resultado;
    }
    

}
