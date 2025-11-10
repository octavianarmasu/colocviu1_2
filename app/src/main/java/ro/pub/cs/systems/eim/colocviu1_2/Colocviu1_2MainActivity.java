package ro.pub.cs.systems.eim.colocviu1_2;

import android.content.Intent;
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

public class Colocviu1_2MainActivity extends AppCompatActivity {
    public static class Constants {
        public static final String COMPUTE = "COMPUTE";
        public static final String RES = "RES";
        public static final int SEC_CODE = 1;
        public static final String SUM = "SUM";
    }

    private EditText nextTerms, allTerms;
    private Button add, compute;
    private int sumSaved;

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
}