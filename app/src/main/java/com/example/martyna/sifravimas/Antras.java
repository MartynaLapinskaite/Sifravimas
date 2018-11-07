package com.example.martyna.sifravimas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import java.math.BigInteger;
import java.util.Map;


public class Antras extends AppCompatActivity {

    private EditText irasomass;
    private TextView rezultatass;
    private Button copyy, mygtukass, issifravimass;
    private String publicKey= "";
    private String privateKey= "";
    private byte[] encodeData=null;
    private String rez;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sifravimas1);

        irasomass = (EditText) findViewById(R.id.irasomass);
        rezultatass = (TextView) findViewById(R.id.rezultatass);
        copyy=(Button) findViewById(R.id.copyy);
        mygtukass=(Button) findViewById(R.id.mygtukass);
        issifravimass=(Button) findViewById(R.id.issifravimass);

        irasomass.addTextChangedListener(loginTextWatcher);

        try{
            Map<String, Object> keyMap=rsa.initKey();
            publicKey=rsa.getPublicKey(keyMap);
            privateKey=rsa.getPrivateKey(keyMap);
        } catch (Exception e){
            e.printStackTrace();
        }
        copyy=(Button)findViewById(R.id.copyy);
        copyy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rez = rezultatass.getText().toString();
                irasomass.setText(rez);
            }
        });
    }

    public void encrypt(View v){
        byte[] rsaData = irasomass.getText().toString().getBytes();

        try {
            encodeData = rsa.encryptByPublicKey(rsaData, getPublicKey());
            String encodeStr = new BigInteger(1, encodeData).toString();
            rezultatass.setText(encodeStr);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void decrypt(View v){

        try {
            byte[] decodeData = rsa.encryptByPrivateKey(encodeData, getPrivateKey());
            String decodeStr = new String(decodeData);
            rezultatass.setText(decodeStr);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getPublicKey(){
        return publicKey;
    }

    public String getPrivateKey(){
        return privateKey;
    }

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String irasomas1 = irasomass.getText().toString().trim();

            mygtukass.setEnabled(!irasomas1.isEmpty());
            issifravimass.setEnabled(!irasomas1.isEmpty());
            copyy.setEnabled(!irasomas1.isEmpty());
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

}
