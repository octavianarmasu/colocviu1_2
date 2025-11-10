package ro.pub.cs.systems.eim.colocviu1_2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ro.pub.cs.systems.eim.colocviu1_2.service.Colocviu1_2Service;

public class Colocviu1_2MainActivity extends AppCompatActivity {
    public static class Constants {
        public static final String COMPUTE = "COMPUTE";
        public static final String RES = "RES";
        public static final int SEC_CODE = 1;
        public static final String SUM = "SUM";
        public static final String GET_SUM = "GET_SUM";
        public static final String THREAD = "THREAD";
        public static final String ACTION = "ACTION";
        public static final String SENT = "SENT";
    }

    private EditText nextTerms, allTerms;
    private Button add, compute;
    private int sumSaved;
    IntentFilter intentFilter = new IntentFilter();

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();

    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            int data = intent.getIntExtra(Constants.SENT, 0);
            Log.d(Constants.SENT, "Received broadcast: " + action + " -> " + data);
        }

    }

    private ButtonClickListener buttonClickListener = new ButtonClickListener();

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String text = nextTerms.getText().toString().trim();
            String nextText = allTerms.getText().toString();

            if (view.getId() == R.id.add) {
                if (isInteger(text)) {
                    nextText += text + "+";
                }
                allTerms.setText(nextText);
                nextTerms.setText("");
            }
            if (view.getId() == R.id.compute) {
                Intent intent = new Intent(getApplicationContext(), Colocviu1_2SecondaryActivity.class);
                intent.putExtra(Constants.COMPUTE, allTerms.getText().toString());
                startActivityForResult(intent, Constants.SEC_CODE);
            }
            if (getSum(allTerms.getText().toString()) > 10) {
                Intent intent = new Intent(getApplicationContext(), Colocviu1_2Service.class);
                intent.putExtra(Constants.GET_SUM, getSum(allTerms.getText().toString()));
                getApplicationContext().startService(intent);
            }

        }
    }

    private int getSum(String text) {
        int sum = 0;
        for (int i = 0; i < text.length(); i++ ){
            if (text.charAt(i) != '+') {
                String intHelper = String.valueOf(text.charAt(i));
                sum += Integer.parseInt(intHelper);
            }
        }
        return sum;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_2_main);

        nextTerms = findViewById(R.id.next_term);
        allTerms = findViewById(R.id.all_terms);
        add = findViewById(R.id.add);
        compute = findViewById(R.id.compute);
        add.setOnClickListener(buttonClickListener);
        compute.setOnClickListener(buttonClickListener);

        intentFilter.addAction(Constants.ACTION);
    }

    private boolean isInteger(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == Constants.SEC_CODE) {
            int value = intent.getIntExtra(Constants.RES, 0);
            Toast.makeText(this, "The activity returned with result " + value, Toast.LENGTH_LONG).show();
            sumSaved = value;
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d("COLOCVIU", "onSaveInstance()");

        super.onSaveInstanceState(outState);
        outState.putInt(Constants.SUM, sumSaved);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d("COLOCVIU", "onRestoreInstanceState()");
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey(Constants.SUM)) {
            sumSaved = savedInstanceState.getInt(Constants.SUM);
        }
        Log.d("COLOCVIU", "Saved sum:" + sumSaved);

    }
    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, Colocviu1_2Service.class);
        stopService(intent);
        super.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(messageBroadcastReceiver, intentFilter, Context.RECEIVER_NOT_EXPORTED);
        } else {
            registerReceiver(messageBroadcastReceiver, intentFilter);
        }
    }
    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }
}