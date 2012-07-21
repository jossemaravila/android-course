package br.com.caelum.alunos;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class BatteryReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        int level = intent.getIntExtra("level", 0);
        Toast.makeText(context, "N’vel da Bateria: " + level + "%", Toast.LENGTH_LONG);
    }

}
