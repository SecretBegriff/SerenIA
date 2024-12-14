package com.triforce.serenia10;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "SerenIA.db";
    public static final String EMAIL = "email";
    public static final String SESIONES_REAL = "sesiones_realizadas";
    public static final String PAIS_ORIGEN = "pais_origen";
    public static final String NOMBRE = "nombre";
    public static final String RACHA = "racha";
    public static final String NO_MED_REALIZADAS = "no_med_realizadas";
    public static final String ID = "id";
    public static final String DURACION = "duracion";
    public static final String FECHA_PROGRAMADA = "fecha_programada";
    public static final String CONTRASENA = "contrasena";


    public DataBaseHelper(Context context) {super(context, "SerenIA.db", null, 1);}


    public String[] getNacionalidadYMeditaciones(long userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] result = new String[2];
        result[0] = null;
        result[1] = "0";

        Cursor cursor = db.rawQuery(
                "SELECT pais_origen, (SELECT COUNT(*) FROM actividades WHERE usuario_id = ?) FROM usuario WHERE _id = ?",
                new String[]{String.valueOf(userId), String.valueOf(userId)}
        );

        if (cursor.moveToFirst()) {
            result[0] = cursor.getString(0); // pais_origen
            result[1] = String.valueOf(cursor.getInt(1)); // Número de meditaciones
        }
        cursor.close();
        return result;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table usuario (_id integer primary key autoincrement, email text, contrasena text, sesiones_realizadas integer, pais_origen text, nombre text, racha integer, no_med_realizadas integer)");
        ContentValues cv = new ContentValues();
        cv.put(EMAIL, "james.c.mcreynolds@example-pet-store.com");
        cv.put(CONTRASENA, "1234");
        cv.put(SESIONES_REAL, 0);
        cv.put(PAIS_ORIGEN, "España");
        cv.put(NOMBRE, "Pablo");
        cv.put(RACHA, 0);
        cv.put(NO_MED_REALIZADAS, 0);
        db.insert("usuario", EMAIL, cv);



        db.execSQL("create table actividades (_id integer primary key autoincrement, nombre text, duracion text, fecha_programada text, usuario_id integer)");
    }

    public String getNombreUsuarioById(long userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String nombre = null;

        Cursor cursor = db.rawQuery("SELECT nombre FROM usuario WHERE _id = ?", new String[]{String.valueOf(userId)});
        if (cursor.moveToFirst()) {
            nombre = cursor.getString(0);
        }
        cursor.close();
        return nombre;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        android.util.Log.d("DataBaseHelper", "Actializando ando ando ando...");
    }
}
