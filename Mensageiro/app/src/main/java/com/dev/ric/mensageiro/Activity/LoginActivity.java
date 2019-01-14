package com.dev.ric.mensageiro.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.ric.mensageiro.CadastroActivity;
import com.dev.ric.mensageiro.R;
import com.dev.ric.mensageiro.config.ConfiguracaoFirebase;
import com.dev.ric.mensageiro.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class LoginActivity extends AppCompatActivity {

    //Declaração das variáveis dos elementos de interface
    private EditText etEmail, etSenha;
    private TextView tvCadastrar;
    private Button btnEntar;
    private Usuario usuario;

    DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebaseDB();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //referenciaFirebase.setValue("Bombando");

        //Linkando elementos de interface
        etEmail = (EditText) findViewById(R.id.etEmail);
        etSenha = (EditText) findViewById(R.id.etSenha);
        tvCadastrar = (TextView) findViewById(R.id.tvCadastrar);
        btnEntar = (Button) findViewById(R.id.btnEntrar);
        usuario = new Usuario();

        verificarUsuarioLogado();

        btnEntar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario.setEmail(etEmail.getText().toString());
                usuario.setSenha(etSenha.getText().toString());
                loginUsuario();
            }
        });


        tvCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCadastroUsuarioActivity();
            }
        });
    }


    private void verificarUsuarioLogado(){
        FirebaseAuth auth = ConfiguracaoFirebase.getFirebaseAuth();
        FirebaseUser user = auth.getCurrentUser();
        if(user != null){
            abrirTelaPrincipal();
        }else{

        }

    }

    private void loginUsuario(){
        FirebaseAuth auth = ConfiguracaoFirebase.getFirebaseAuth();
        auth.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    abrirTelaPrincipal();
                    Toast.makeText(LoginActivity.this, "Sucesso ao fazer o login", Toast.LENGTH_SHORT).show();
                }else{
                    try{
                        throw task.getException();
                    }catch (FirebaseAuthInvalidUserException e){
                        Toast.makeText(LoginActivity.this, "Usuário inválido!", Toast.LENGTH_LONG).show();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        Toast.makeText(LoginActivity.this, "Senha incorreta!", Toast.LENGTH_LONG).show();
                    }
                    catch (Exception e){
                        Log.i("Exception: ", e.toString());
                    }
                }
            }
        });
    }

    private void abrirCadastroUsuarioActivity(){
        Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(intent);
        finish();
    }

    private void abrirTelaPrincipal(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
