package com.firstapp.touchbytouch;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

//from gemini ngani
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    //declaring na of variabless
    TextView display;
    double firstnum = 0;
    String operator = "";
    boolean newop = true;
    String historyText = "";


    //When newop is true, the onNumberClick method says: "Delete whatever is there and start fresh."
    //When newop is false, it says: "Keep adding digits to the end of the current number."


    @Override
    //ambot wa nako kasabot diri
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        display = findViewById(R.id.textView2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

        // 1. Number Click Logic (0-9 and Dot)
        public void onNumberClick(View view) {
            Button button = (Button) view;
            String clickedNumber = button.getText().toString();

            if (newop || display.getText().toString().equals("0")) {
                display.setText(clickedNumber);
            } else {
                display.append(clickedNumber);
            }
            newop = false;  //not to put the new number to the equals calculated
        }



        // 2. Operator Logic (+, -, ×, ÷)
        public void onOperatorClick(View view) {
            Button button = (Button) view;
            operator = button.getText().toString();
            firstnum = Double.parseDouble(display.getText().toString());
                                                //takes that "text" and converts it into a Double (a math-ready number with decimals)
            newop = true;
        }




        // 3. Equal Logic and history
        public void onEqualClick(View view) {
            double secondNum = Double.parseDouble(display.getText().toString());
            double result = 0;

            switch (operator) {
                case "+": result = firstnum + secondNum; break;
                case "-": result = firstnum - secondNum; break;
                case "×": result = firstnum * secondNum; break;
                case "÷":
                    if (secondNum != 0) result = firstnum / secondNum;
                    else { display.setText("Error"); return; }
                    break;
            }

            historyText = firstnum + " " + operator + " " + secondNum + " = " + result;

            // Clean result: removes .0 if it's a whole number
            if (result == (long) result) {
                display.setText(String.format("%d", (long) result));
            } else {
                display.setText(String.valueOf(result));
            }
            newop = true;
        }


        // 4. Utility Logic (AC, Delete, Percent)
        public void onACClick(View view) {
            display.setText("0");
            firstnum = 0;
            operator = "";
            newop = true;
        }

        public void onDelClick(View view) {
            String val = display.getText().toString();
            if (val.length() > 0 && !val.equals("0")) {
                val = val.substring(0, val.length() - 1);
                if (val.isEmpty()) val = "0";
                display.setText(val);
            }
        }

        public void onPercentClick(View view) {
            double val = Double.parseDouble(display.getText().toString());
                                                          // takes that "text" and converts it into a Double (a math-ready number with decimals)
            display.setText(String.valueOf(val / 100));
            newop = true;
        }


    // This goes after onPercentClick haaa like a brandnew instruction
    public void onHistoryClick(View view) {
        if (historyText.isEmpty()) {
            android.widget.Toast.makeText(this, "No history yet! 🕒", android.widget.Toast.LENGTH_SHORT).show();
        } else {
            android.widget.Toast.makeText(this, "Last calculation: " + historyText, android.widget.Toast.LENGTH_LONG).show();
        }

    }
    }
