package com.example.appclinica.ui.helpers;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context) {
        super(context, "GestionCitas.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS Sedes (IdSede INTEGER PRIMARY KEY AUTOINCREMENT, Nombre TEXT NOT NULL, Direccion TEXT NOT NULL, Distrito TEXT NOT NULL, UbicacionLatitud NUMERIC(11,8) NOT NULL, UbicacionLongitud NUMERIC(11,8) NOT NULL, FotoUrl TEXT NOT NULL)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Sedes");
        onCreate(db);
    }
}
