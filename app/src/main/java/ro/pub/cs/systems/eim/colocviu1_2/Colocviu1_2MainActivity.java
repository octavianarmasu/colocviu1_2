package ro.pub.cs.systems.eim.colocviu1_2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Colocviu1_2MainActivity extends AppCompatActivity {

    private EditText nextTerms, allTerms;
    private Button add, compute;

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

        }
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
}