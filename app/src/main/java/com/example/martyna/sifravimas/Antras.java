package com.example.martyna.sifravimas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigInteger;
import java.util.Map;


public class Antras extends AppCompatActivity {

    private EditText irasomass;
    private TextView rezultatass;
    private String publicKey= "";
    private String privateKey= "";
    private byte[] encodeData=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sifravimas1);

        irasomass = (EditText) findViewById(R.id.irasomass);
        rezultatass = (TextView) findViewById(R.id.rezultatass);

        try{
            Map<String, Object> keyMap=rsa.initKey();
            publicKey=rsa.getPublicKey(keyMap);
            privateKey=rsa.getPrivateKey(keyMap);
        } catch (Exception e){
            e.printStackTrace();
        }
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

}
