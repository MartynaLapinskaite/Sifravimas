package com.example.martyna.sifravimas;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Pirmas extends AppCompatActivity {

    EditText irasomas, raktas;
    TextView rezultatas;
    Button mygtukas;
    String rez;
    String AES ="AES";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sifravimas);

        irasomas = (EditText) findViewById(R.id.irasomas);
        raktas= (EditText) findViewById(R.id.raktas);

        rezultatas = (TextView) findViewById(R.id.rezultatas);
        mygtukas = (Button) findViewById(R.id.mygtukas);

        irasomas.addTextChangedListener(loginTextWatcher);
        raktas.addTextChangedListener(loginTextWatcher);

        mygtukas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            try{
                rez = encrypt(irasomas.getText().toString(), raktas.getText().toString());
                rezultatas.setText(rez);
            }catch (Exception e) {
                e.printStackTrace();
            }
            }
        });


    }

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {


        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String irasomas1 = irasomas.getText().toString().trim();
            String raktas1 = raktas.getText().toString().trim();

            mygtukas.setEnabled(!irasomas1.isEmpty() && !raktas1.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private String encrypt(String Data, String password) throws Exception {
        SecretKeySpec key = generateKey(password);
        Cipher c= Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal= c.doFinal(Data.getBytes());
        String encyptedValue = Base64.encodeToString(encVal,Base64.DEFAULT);
        return encyptedValue;
    }

    private SecretKeySpec generateKey(String password) throws Exception{
        final MessageDigest digest =MessageDigest.getInstance("SHA-256");
        byte[] bytes = password.getBytes("UTF-8");
        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        return secretKeySpec;


    }
}
