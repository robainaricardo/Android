package com.dev.ric.mensageiro;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dev.ric.mensageiro.helper.Permissao;
import com.dev.ric.mensageiro.helper.Preferencias;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.common.SignInButton;

import java.util.HashMap;
import java.util.Random;
import java.util.TreeMap;

public class LoginActivity extends AppCompatActivity {

    //Declaração das variáveis dos elementos de interface
    private EditText etNome, etCodPais, etCodEstado, etNumFone;
    private Button btnCadastrar;
    private String[] permissoesNecessarias = new String[]{
            Manifest.permission.SEND_SMS
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Permissao.validarPermissoes(1,  this, permissoesNecessarias);

        //Linkando elementos de interface
        etNome = (EditText) findViewById(R.id.etNome);
        etCodPais = (EditText) findViewById(R.id.etCodPais);
        etCodEstado = (EditText) findViewById(R.id.etCodEstado);
        etNumFone = (EditText) findViewById(R.id.etNumFone);
        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);

        //Mascara para número de telefone
        SimpleMaskFormatter simpleMaskTelefone = new SimpleMaskFormatter("N NNNN NNNN");
        SimpleMaskFormatter simpleMaskPais = new SimpleMaskFormatter("+NN");
        SimpleMaskFormatter simpleMaskEstado = new SimpleMaskFormatter("NN");

        MaskTextWatcher maskTelefone = new MaskTextWatcher(etNumFone, simpleMaskTelefone);
        MaskTextWatcher maskPais = new MaskTextWatcher(etCodPais, simpleMaskPais);
        MaskTextWatcher maskEstado = new MaskTextWatcher(etCodEstado, simpleMaskEstado);

        etNumFone.addTextChangedListener(maskTelefone);
        etCodPais.addTextChangedListener(maskPais);
        etCodEstado.addTextChangedListener(maskEstado);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeUsuario = etNome.getText().toString();
                String telefoneUsuario = etCodPais.getText().toString() +
                        etCodEstado.getText().toString() +
                        etNumFone.getText().toString();

                telefoneUsuario = telefoneUsuario.replace(" ", "");
                //telefoneUsuario = telefoneUsuario.replace("+", "");

                //Gerar Token
                Random randomico = new Random();
                int numeroRandomico = randomico.nextInt(8999) + 1000;

                String token = String.valueOf(numeroRandomico);

                //Salvar dados para a validação por sharedPreferences
                Preferencias preferencias = new Preferencias(LoginActivity.this);
                preferencias.salvarUsuarioPreferencias(nomeUsuario, telefoneUsuario, token);

                //HashMap<String, String> usuario = preferencias.getDatosUsuario();
                //Log.i("Token", "T:" + usuario.get("token"));

                //Envio do SMS
                String mensagem = "Mensageiro Código de confirmação: " + token;
                boolean smsEnviado = enviarSMS(telefoneUsuario, mensagem);

                if(smsEnviado){
                    Intent intent = new Intent(LoginActivity.this, ValidadorActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "Erro ao enviar SMS, tente novamente.", Toast.LENGTH_LONG).show();
                }



            }
        });

    }

    private boolean enviarSMS(String telefone, String mensagem){
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefone, null, mensagem, null, null);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //Tratamento se o usuario negou uma permissão necessária
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int resultado: grantResults){
            if(resultado == PackageManager.PERMISSION_DENIED){
                alertaValidacaoPermissao();
            }
        }
    }

    //Alerta de quando o usuário negar as permissões
    private void alertaValidacaoPermissao(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões Negadas");
        builder.setMessage("Para utilizar o app é preciso aceitar as permissões");
        builder.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

}
