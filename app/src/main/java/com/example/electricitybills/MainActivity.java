package com.example.electricitybills;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView tvOutput;
    Button btnCalculate;
    EditText etValue;
    EditText rebate;
    Button btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        tvOutput = findViewById(R.id.tvOutput);
        btnCalculate = findViewById(R.id.btnCalculate);
        etValue = findViewById(R.id.etValue);
        rebate = findViewById(R.id.rebate);
        btnClear = findViewById(R.id.btnClear);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number1 = etValue.getText().toString();
                String number2 = rebate.getText().toString();


                try{
                    double Number1 = Double.parseDouble(number1);
                    double Number2 = Double.parseDouble(number2);
                    if (Number2 < 0 || Number2 > 5) {
                        Toast.makeText(getApplication(), "Rebate must be between 0% and 5%", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    double totalCost = 0;


                    if (Number1 <= 200) {
                        totalCost = Number1 * 0.218;
                    } else if (Number1 <= 300) {
                        totalCost = (200 * 0.218) + ((Number1 - 200) * 0.334);
                    } else if (Number1 <= 600) {
                        totalCost = (200 * 0.218) + (100 * 0.334) + ((Number1 - 300) * 0.516);
                    } else {
                        totalCost = (200 * 0.218) + (100 * 0.334) + (300 * 0.516) + ((Number1 - 600) * 0.546);
                    }


                    double Rebate = totalCost*(Number2 / 100);
                    double finalCost = totalCost - Rebate;
                    String output = String.format("Bill Before Rebate: RM %.2f\nRebate: RM %.2f\nBill After Rebate: RM %.2f", totalCost, Rebate, finalCost);
                    tvOutput.setText(output);
                } catch (NumberFormatException nfe) {
                    tvOutput.setText("Calculate Bills");
                    Toast.makeText(getApplication(), "Please enter number at all fields", Toast.LENGTH_SHORT).show();

                }

            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              clearFields();
            }
        });

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

    }

    private void clearFields() {
        etValue.setText("");
        rebate.setText("");
        tvOutput.setText("Calculate Bills");
    }

    @Override
      public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selected = item.getItemId();

       if (selected == R.id.menuAbout) {
            Toast.makeText(this, "About Clicked", Toast.LENGTH_SHORT).show();
            Intent aboutIntent = new Intent(this,AboutActivity.class);
            startActivity(aboutIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}

