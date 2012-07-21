package br.com.caelum.alunos;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        byte[][] pdus = (byte[][]) bundle.get("pdus");
        
        SmsMessage[] messages = new SmsMessage[pdus.length];

        for (int i = 0; i < pdus.length; i++) {
            SmsMessage message = SmsMessage.createFromPdu(pdus[i]);

            String telefone = message.getDisplayOriginatingAddress();

            Aluno aluno = new AlunoDao(context).buscarPorTelefone(telefone);

            if (aluno != null) {
                Toast.makeText(context, message.getMessageBody(), Toast.LENGTH_LONG);
                
                //MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.msg);
                //mediaPlayer.start();
            }
        }
    }

}
