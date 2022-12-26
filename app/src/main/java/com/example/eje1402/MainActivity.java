package com.example.eje1402;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {
    public EditText txtUsu,txtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtUsu=findViewById(R.id.txtUsuario);
        txtPass=findViewById(R.id.txtPassword);
    }
    public Connection conexionBD(){
        Connection cnn=null;
        try {
            StrictMode.ThreadPolicy pol=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(pol);
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            cnn= DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.1.7:1433/BDCarritoG1;"+
                    "instance=SQL2019G2;user=sa;password=12345");
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return cnn;
    }

    public void EnvioPrueba(View view){
        Intent x=new Intent(this,frmManTipoUser.class);
        startActivity(x);
    }
    public void Consulta(View view){
        try {
            Statement st=conexionBD().createStatement();
            ResultSet rs=st.executeQuery("SELECT * FROM usuarios " +
                    "where logeo='"+txtUsu.getText().toString()+"' and clave='"+txtPass.getText().toString()+"'");
            if(rs.next()){
/*
* TIU00003
TIU00001
TIU00002*/
                String tipoU=rs.getString(2);
                Toast.makeText(getApplicationContext(),"Conexion establecida Version 2.0 "+rs.getString(2),Toast.LENGTH_SHORT).show();
                if(tipoU.compareTo("TIU00001")==0){//cliente
                    Intent x=new Intent(this,frmMainCliente.class);
                    startActivity(x);
                }
                if(tipoU.compareTo("TIU00002")==0){//admin
                    Intent x=new Intent(this,frmMainAdmin.class);
                    startActivity(x);
                }
                if(tipoU.compareTo("TIU00003")==0){//invitado
                    Intent x=new Intent(this,frmMainInvitado.class);
                    startActivity(x);
                }

            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}