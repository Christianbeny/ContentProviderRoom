package net.ivanvega.basededatosconroomb;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.UserDictionary;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import net.ivanvega.basededatosconroomb.data.AppDataBase;
import net.ivanvega.basededatosconroomb.data.User;
import net.ivanvega.basededatosconroomb.data.UserDao;
import net.ivanvega.basededatosconroomb.provider.UsuarioProviderContract;

public class MainActivity extends AppCompatActivity {
    Button btnIn, btnQue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ContactsContract.Contacts.CONTENT_URI;


        btnIn = findViewById(R.id.btnInsert);
        btnQue = findViewById(R.id.btnQuery);

        btnIn.setOnClickListener(view -> {

            AppDataBase db =
                    AppDataBase.getDataBaseInstance(getApplication());

            UserDao dao = db.getUserDao();

            AppDataBase.databaseWriteExecutor.execute(() -> {
                User u = new User();
                u.uid=0;

                u.firstName = "Juan";
                u.lastName = "Peres";
                dao.insertAll(u);
                //Toast.makeText(this, "insertado", Toast.LENGTH_SHORT).show();
            });

        });

        btnQue.setOnClickListener(view -> {
            AppDataBase db =
                    AppDataBase.getDataBaseInstance(getApplication());

            UserDao dao = db.getUserDao();


            AppDataBase.databaseWriteExecutor.execute(() -> {
                for(User item : dao.getAll()){
                    Log.d("Usuario", item.uid + " " +  item.firstName
                            + " " + item.lastName);
                }
            });


        });

    }
}