package net.ivanvega.clientecontentprovider;

import android.net.Uri;

public class UsuarioProviderContract {

    public static final Uri CONTENT_URI =
           Uri.parse( "content://net.ivanvega.basededatosconroomb.provider/usuario");

    public static final String ID_COLUMN = "uid";
    public static final String FIRSTNAME_COLUMN = "first_name";
    public static final String LASTNAME_COLUMN = "last_name";

    public static final String[] COLUMNS = {
        "uid", "first_name", "last_name"
    };

}
