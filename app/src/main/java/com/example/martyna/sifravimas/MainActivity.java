package com.example.martyna.sifravimas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {
    private Button aes;
    private Button rsaa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aes = (Button) findViewById(R.id.aes);
        aes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openPirmas();
            }
        });

        rsaa = (Button)findViewById(R.id.rsaa);
        rsaa.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openAntras();
            }
        });

    }
    public void openPirmas(){
        Intent intent = new Intent(this, Pirmas.class);
        startActivity(intent);
    }

    public void openAntras(){
        Intent intent = new Intent(this, Antras.class);
        startActivity(intent);
    }
}
