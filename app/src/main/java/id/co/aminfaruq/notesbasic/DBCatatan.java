package id.co.aminfaruq.notesbasic;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DBCatatan extends SQLiteOpenHelper {
    static abstract class MyColums implements BaseColumns {
        static final String namaTabel = "Catatan";
        static final String id_judul = "ID_judul";
        static final String judul = "Judul";
        static final String isi = "Isi";
    }

    private static final String namaDatabase = "catatan.db";
    private static final int versiDatabase = 1 ;

    //Query for create table
    private static final String SQL_CREATE_TABLE = "CREATE TABLE " + MyColums.namaTabel +
            "(" +MyColums.id_judul+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
            +MyColums.judul+" TEXT NOT NULL, "
            +MyColums.isi+" TEXT NOT NULL)";

    private static final String SQL_DELETE_TABLE = " DROP TABLE IF EXISTS "+MyColums.namaTabel;

    public DBCatatan(Context context){
        super(context,namaDatabase,null,versiDatabase);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(SQL_DELETE_TABLE);
        onCreate(sqLiteDatabase);
    }
}
