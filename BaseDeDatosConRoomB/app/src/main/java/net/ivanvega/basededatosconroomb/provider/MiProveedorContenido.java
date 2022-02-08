package net.ivanvega.basededatosconroomb.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.ivanvega.basededatosconroomb.data.AppDataBase;
import net.ivanvega.basededatosconroomb.data.User;
import net.ivanvega.basededatosconroomb.data.UserDao;

import java.util.List;

public class MiProveedorContenido
        extends ContentProvider {

    /*
        content://net.ivanvega.basededatosconroomb.provider/usuario ->  insert y query
        content://net.ivanvega.basededatosconroomb.provider/usuario/# -> delete, query y update
        content://net.ivanvega.basededatosconroomb.provider/usuario/*  ->  query

     */

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static
    {
        sURIMatcher.addURI("net.ivanvega.basededatosconroomb.provider",
                "usuario", 1);
        sURIMatcher.addURI("net.ivanvega.basededatosconroomb.provider",
                "usuario/#", 2);
        sURIMatcher.addURI("net.ivanvega.basededatosconroomb.provider",
                "usuario/*", 3);
    }


    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings,
                        @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {


        AppDataBase db =
                AppDataBase.getDataBaseInstance(getContext());

        UserDao dao = db.getUserDao();

        MatrixCursor cursor = new MatrixCursor(new String[]
                {"uid", "first_name", "last_name"});

          switch ( sURIMatcher.match(uri)){
              case 1:

                      for(User item : dao.getAll()){
                          cursor.newRow().add("uid", item.uid)
                                  .add("first_name", item.firstName)
                                  .add("last_name", item.lastName);

                          Log.d("TABla Usuario", item.uid + " " +  item.firstName
                                  + " " + item.lastName);
                      }


                  break;

              case 2:

                  break;

              case 3:

                  break;
          }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        /*Tipos MIME
            Documentos HTML     "text/html"
            Documentos XML      "text/xml"
            Documentos JavaScript

            Imagenes  PNG    "images/png"
            Imagenes JPG

            tipos mime para android

            vnd.android.cursor.item/vnd.net.ivanvega.basededatosconroomb.provider.usuario
            vnd.android.cursor.dir/vnd.net.ivanvega.basededatosconroomb.provider.usuario


         */

        String tipoMIME = "";

        switch (sURIMatcher.match(uri)){
            case 1:
                tipoMIME = "vnd.android.cursor.dir/vnd.net.ivanvega.basededatosconroomb.provider.usuario";
                break;

            case 2:
                tipoMIME = "vnd.android.cursor.item/vnd.net.ivanvega.basededatosconroomb.provider.usuario";

            case 3:
                tipoMIME = "vnd.android.cursor.dir/vnd.net.ivanvega.basededatosconroomb.provider.usuario";
                break;
        }


        return tipoMIME;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri,
                      @Nullable ContentValues contentValues)
    {
        Uri uriR=  Uri.withAppendedPath(uri, "-1");

        switch (sURIMatcher.match(uri)){
            case 1:
                User userInsert = new User();
                userInsert.firstName =
                        contentValues.getAsString(UsuarioProviderContract.FIRSTNAME_COLUMN);
                userInsert.lastName =
                        contentValues.getAsString(UsuarioProviderContract.LASTNAME_COLUMN);
                AppDataBase db =
                        AppDataBase.getDataBaseInstance(getContext());

                UserDao dao = db.getUserDao();

                 long idNewRow =  dao.insertUser(userInsert);

                 uri =
                         Uri.withAppendedPath(uri,
                                 String.valueOf(idNewRow));
                break;
        }
        return uri;
    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {


        int filasAfectadas = -1;

        switch (sURIMatcher.match(uri)){

            case 2:
                int idDelete = Integer.valueOf( uri.getLastPathSegment());

                AppDataBase db =
                        AppDataBase.getDataBaseInstance(getContext());

                UserDao dao = db.getUserDao();

                List<User> lstUserDelete = dao.loadAllByIds(new int[]{idDelete});

                User userDelete = lstUserDelete.get(0);

                filasAfectadas = dao.delete(userDelete);

                break;
        }
        return filasAfectadas;
    }

    @Override
    public int update(@NonNull Uri uri,
                      @Nullable ContentValues contentValues,

                      @Nullable String s, @Nullable String[] strings) {

        int filasAfectadas = -1;

        switch (sURIMatcher.match(uri)){

            case 3:
                int idUpdate = Integer.valueOf( uri.getLastPathSegment());

                AppDataBase db =
                        AppDataBase.getDataBaseInstance(getContext());

                UserDao dao = db.getUserDao();

                List<User>  lstUserUdate = dao.loadAllByIds(new int[]{idUpdate});

                User userUpdate = lstUserUdate.get(0);

                userUpdate.firstName =
                        contentValues.getAsString(
                                UsuarioProviderContract.FIRSTNAME_COLUMN);

                userUpdate.lastName =
                        contentValues.getAsString(
                                UsuarioProviderContract.LASTNAME_COLUMN);


                filasAfectadas = dao.updateUser(userUpdate);

                break;
        }

        return filasAfectadas;

    }

}
