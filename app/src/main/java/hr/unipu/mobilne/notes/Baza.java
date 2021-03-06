package hr.unipu.mobilne.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Baza extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "biljeske.db";
    public static final String TABLE_NAME = "biljeske";
    public static final String COL_ID = "id";
    public static final String COL_NASLOV = "naslov";
    public static final String COL_TEKST = "tekst";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE = "CREATE TABLE " + TABLE_NAME + " ( " +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_NASLOV + " TEXT, " +
            COL_TEKST + " TEXT " +
            ")";

    public Baza(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean novaBiljeska(Biljeska biljeska) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NASLOV, biljeska.getNaslov());
        contentValues.put(COL_TEKST, biljeska.getTekst());

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public boolean izmijeniBiljesku(Biljeska biljeska) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NASLOV, biljeska.getNaslov());
        contentValues.put(COL_TEKST, biljeska.getTekst());
        int id = biljeska.getId();

        long result = db.update(TABLE_NAME, contentValues, COL_ID + "="+id, null);
        return result != -1;
    }

    public boolean izbrisiBiljesku(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COL_ID + "=" + id, null) > 0;
    }

    public boolean izbrisiSveBiljeske() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, null, null) >= 0;
    }

    public List<Biljeska> listaBiljeski() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Biljeska> biljeske = new ArrayList<>();
        String querry = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(querry, null);

        while (cursor.moveToNext()) {
            Biljeska biljeska = new Biljeska();

            biljeska.setId(cursor.getInt(cursor.getColumnIndex(COL_ID)));
            biljeska.setNaslov(cursor.getString(cursor.getColumnIndex(COL_NASLOV)));
            biljeska.setTekst(cursor.getString(cursor.getColumnIndex(COL_TEKST)));

            biljeske.add(biljeska);
        }

        return biljeske;
    }

    public Biljeska nadjiBiljesku(int id) {
        Biljeska biljeska = new Biljeska();
        SQLiteDatabase db = this.getWritableDatabase();
        String querry = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_ID + " = " + id;
        Cursor cursor = db.rawQuery(querry, null);
        if (cursor != null && cursor.moveToFirst()) {
            biljeska.setId(cursor.getInt(cursor.getColumnIndex(COL_ID)));
            biljeska.setNaslov(cursor.getString(cursor.getColumnIndex(COL_NASLOV)));
            biljeska.setTekst(cursor.getString(cursor.getColumnIndex(COL_TEKST)));
        }

        return biljeska;
    }


}
