package com.dev.ric.mensageiro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dev.ric.mensageiro.helper.Preferencias;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;

public class ValidadorActivity extends AppCompatActivity {

    private EditText etCodigo;
    private Button btnValidar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validador);

        etCodigo = findViewById(R.id.etCodigo);
        btnValidar = findViewById(R.id.btnValidar);

        //mascara no codigo
        SimpleMaskFormatter simpleMaskCodigo = new SimpleMaskFormatter("NNNN");
        MaskTextWatcher maskCodigo = new MaskTextWatcher(etCodigo, simpleMaskCodigo);
        etCodigo.addTextChangedListener(maskCodigo);


        btnValidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Recuperar dados das preferencias
                Preferencias preferencias = new Preferencias(ValidadorActivity.this);
                HashMap<String, String> usuario = preferencias.getDatosUsuario();

                String tokenGerado = usuario.get("token");
                String tokenDigitado = etCodigo.getText().toString();

                if(tokenGerado.equals(tokenDigitado)){
                    Toast.makeText(ValidadorActivity.this, "Validado!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ValidadorActivity.this, "Código Incorreto!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
