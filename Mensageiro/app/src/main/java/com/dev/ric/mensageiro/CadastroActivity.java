package com.dev.ric.mensageiro;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dev.ric.mensageiro.config.ConfiguracaoFirebase;
import com.dev.ric.mensageiro.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class CadastroActivity extends AppCompatActivity {

    private EditText etNome, etEmail, etSenha;
    private Button btnCadastar;
    private Usuario usuario;

    FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        etNome = (EditText) findViewById(R.id.etNome);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etSenha = (EditText) findViewById(R.id.etSenha);
        btnCadastar = (Button) findViewById(R.id.btnCadastrar);

        btnCadastar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = etNome.getText().toString();
                String email = etEmail.getText().toString();
                String senha = etSenha.getText().toString();
                usuario = new Usuario(nome, email, senha);
                cadastarUsuario(usuario);
            }
        });




    }

    private void cadastarUsuario(final Usuario usuario){
        autenticacao = ConfiguracaoFirebase.getFirebaseAuth();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CadastroActivity.this, "Sucesso ao cadastar o usuário.", Toast.LENGTH_LONG).show();
                    //Recuperando id gerado pelo firebase
                    FirebaseUser firebaseUser = task.getResult().getUser();
                    usuario.setId(firebaseUser.getUid());
                    //salvando dados do usuario
                    usuario.salvarDados();
                    autenticacao.signOut();
                    finish();
                }else{
                    String erro = "";
                    try{
                        throw task.getException();
                    }catch(FirebaseAuthWeakPasswordException e){
                        erro = "A senha digitada é muito fraca";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        erro = "O email digitado é inválido";
                    }catch (FirebaseAuthUserCollisionException e){
                        erro = "Usuário já cadastrado";
                    }catch (Exception e){
                        Log.i("Exception: ", e.toString());
                    }
                    Toast.makeText(CadastroActivity.this, "Erro: " + erro, Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}
