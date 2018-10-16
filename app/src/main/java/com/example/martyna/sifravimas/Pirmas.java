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
import android.widget.Toast;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;

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
    Button issifravimas;
    String rez;
    String AES ="AES";
    Button copy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sifravimas);

        irasomas = (EditText) findViewById(R.id.irasomas);
        raktas= (EditText) findViewById(R.id.raktas);

        rezultatas = (TextView) findViewById(R.id.rezultatas);
        mygtukas = (Button) findViewById(R.id.mygtukas);
        issifravimas = (Button) findViewById(R.id.issifravimas);

        irasomas.addTextChangedListener(loginTextWatcher);
        raktas.addTextChangedListener(loginTextWatcher);

        copy=(Button)findViewById(R.id.copy);
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rez = rezultatas.getText().toString();
                irasomas.setText(rez);
            }
        });

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

        issifravimas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    rez =decrypt(irasomas.getText().toString(), raktas.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(Pirmas.this, "Klaida", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                rezultatas.setText(rez);
            }
        });
    }



    private String encrypt(String Data, String password) throws Exception {
        SecretKeySpec key = generateKey(password);
        Cipher c= Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal= c.doFinal(Data.getBytes());
        String encyptedValue = Base64.encodeToString(encVal,Base64.DEFAULT);
        return encyptedValue;
    }

    private String decrypt(String irasomas, String password) throws Exception{
        SecretKeySpec key = generateKey(password);
        Cipher c= Cipher.getInstance(AES);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedValue= Base64.decode(irasomas, Base64.DEFAULT);
        byte[] decValue= c.doFinal(decodedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
    }

    private SecretKeySpec generateKey(String password) throws Exception{
        final MessageDigest digest =MessageDigest.getInstance("SHA-256");
        byte[] bytes = password.getBytes("UTF-8");
        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        return secretKeySpec;


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
            issifravimas.setEnabled(!irasomas1.isEmpty() && !raktas1.isEmpty());
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };


}
