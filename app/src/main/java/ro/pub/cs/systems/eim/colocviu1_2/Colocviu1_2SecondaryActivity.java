package ro.pub.cs.systems.eim.colocviu1_2;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Colocviu1_2SecondaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey(Colocviu1_2MainActivity.Constants.COMPUTE)) {
            String text = intent.getStringExtra(Colocviu1_2MainActivity.Constants.COMPUTE);
            assert text != null;
            int sum = getSum(text);
            Intent intentResult = new Intent();
            intentResult.putExtra(Colocviu1_2MainActivity.Constants.RES, sum);
            setResult(RESULT_OK, intentResult);

            finish();
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
}
