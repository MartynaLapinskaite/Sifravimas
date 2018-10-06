package com.example.martyna.sifravimas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {
    private Button aes;

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
    }
    public void openPirmas(){
        Intent intent = new Intent(this, Pirmas.class);
        startActivity(intent);
    }
}
