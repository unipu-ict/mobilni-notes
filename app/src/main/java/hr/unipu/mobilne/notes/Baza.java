package hr.unipu.mobilne.notes;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    public Baza(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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


}
