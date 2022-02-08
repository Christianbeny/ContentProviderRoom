package net.ivanvega.clientecontentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        findViewById(R.id.btnQuery).setOnClickListener(
            v -> consultarCP()
        );

        findViewById(R.id.btnInsert).setOnClickListener(
                v -> {
                    insertCP();
                }
        );
        findViewById(R.id.btnDelete).setOnClickListener(
                v -> deleteCP()
        );

    }

    private void deleteCP() {

        Uri uriDelete = Uri.withAppendedPath(UsuarioProviderContract.CONTENT_URI,"5");

        int filasAfectas = getContentResolver().delete(uriDelete, "", null );

        Log.d("providerusuario", "Filas afectadas: "+ filasAfectas);
    }

    private void consultarCP() {

        Cursor c =  getContentResolver().query(
                UsuarioProviderContract.CONTENT_URI,
                UsuarioProviderContract.COLUMNS,
                null,null,null
        );

        if(c!=null){
            while(c.moveToNext()){
                Log.d("providerusuario", "Usuario: "+ c.getInt(0)
                        + " - " + c.getString(1));
            }
        }else{
            Log.d("providerusuario", "Sin Usuario: ");
        }

        updateCP();

    }


    private void insertCP () {

        ContentValues cv = new ContentValues();

        cv.put(UsuarioProviderContract.FIRSTNAME_COLUMN, "Pablo");
        cv.put(UsuarioProviderContract.LASTNAME_COLUMN, "Secundino");

        Uri uriinsert =
                getContentResolver()
                        .insert(UsuarioProviderContract.CONTENT_URI,
                cv);

        Log.d("providerusuario", uriinsert.toString());
    }

    private void  updateCP(){
        ContentValues cv = new ContentValues();
        cv.put(UsuarioProviderContract.FIRSTNAME_COLUMN, "David");
        cv.put(UsuarioProviderContract.LASTNAME_COLUMN, "Vwga");

        Uri uriUpdate = Uri.withAppendedPath(UsuarioProviderContract.CONTENT_URI,
                "4");

        int filasAfectas = getContentResolver().update(uriUpdate, cv, null, null );

        Log.d("providerusuario", "Filas afectadas: "+ filasAfectas);
    }
    
}