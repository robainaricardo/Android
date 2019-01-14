package com.dev.ric.mensageiro.helper;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permissao {

    public static boolean validarPermissoes(int requestCode, Activity activity, String[] permissoes){

        List<String> listaPermissoes = new ArrayList<String>();

        if(Build.VERSION.SDK_INT >= 23){
            for (String permissao: permissoes){
                boolean validaPermissão = ContextCompat.checkSelfPermission(activity, permissao) ==
                        PackageManager.PERMISSION_GRANTED;
                if(!validaPermissão){
                    listaPermissoes.add(permissao);
                }
            }

            //lista de permissões pendes é vazia
            if(listaPermissoes.isEmpty()) return true;

            //Converter o array list em vetor de string
            String[] novasPermissoes = new String[listaPermissoes.size()];
            listaPermissoes.toArray(novasPermissoes);

            //Solicitar permissão
            ActivityCompat.requestPermissions(activity, novasPermissoes, requestCode);


        }

        return true;
    }



}
