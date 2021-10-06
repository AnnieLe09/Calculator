package com.example.myapplication;

import static java.lang.Integer.parseInt;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView txtViewDisplay;
    int f1;
    char cal;
    boolean flag, equal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cal = '!';
        flag = false;
        equal = false;
        txtViewDisplay = (TextView) findViewById(R.id.textViewDisplay);
        addButtons((GridLayout) findViewById(R.id.mainLayout));

    }

    private void addButtons(GridLayout mainLayout) {
        String[] names = new String[]{
                "7", "8", "9", "+",
                "4", "5", "6", "-",
                "1", "2", "3", "*",
                "AC", "0", "=", "/"
        };
        Button btn;
        for (int i = 0; i < 16; ++i) {
            btn = createButton(65000 + i, names[i]);
            mainLayout.addView(btn);
        }
    }

    private void clearText() {
        txtViewDisplay.setText("");
        flag = false;
        cal = '!';
    }

    private void returnResult() {
        int res;
        String tmp = txtViewDisplay.getText().toString();
        if(tmp.length() == 0) {
            return;
        }
        int f2 = parseInt(tmp);
        if (cal == '+') {
            res = f1 + f2;
        } else if (cal == '-') {
            res = f1 - f2;
        } else if (cal == '*') {
            res = f1 * f2;
        } else if (cal == '/') {
            if (f2 != 0) {
                res = f1 / f2;
            } else {
                clearText();
                return;
            }
        } else {
            res = f2;
        }
        f1 = res;
        txtViewDisplay.setText(Integer.toString(res));
        cal = '!';
    }

    private Button createButton(int id, String name) {
        Button btn = new Button(this);
        btn.setId(id);
        btn.setText(name);
        btn.setTextSize(30);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button btn = (Button) view;
                char tmp = name.charAt(0);
                if (tmp <= '9' && tmp >= '0') {
                    if (!flag) flag = true;
                    txtViewDisplay.setText(txtViewDisplay.getText().toString() + btn.getText().toString());
                    if(equal){
                        txtViewDisplay.setText(btn.getText().toString());
                        equal = false;
                    }
                    else{
                        txtViewDisplay.setText(txtViewDisplay.getText().toString() + btn.getText().toString());
                    }
                } else if (tmp == 'A') {
                    clearText();
                } else if (flag) {
                    if (tmp == '=') {
                        returnResult();
                        equal = true;
                    } else {
                        if (cal != '!') {
                            returnResult();
                        } else {
                            f1 = parseInt(txtViewDisplay.getText().toString());
                        }
                        cal = tmp;
                        txtViewDisplay.setText("");
                    }
                }
            }
        });
        return btn;
    }
}
