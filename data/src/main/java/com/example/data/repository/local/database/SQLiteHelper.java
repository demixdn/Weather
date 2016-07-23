package com.example.data.repository.local.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Aleksandr on 22.07.2016 in Weather.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    private Context context;

    public SQLiteHelper(Context context) {
        super(context, DBConst.DATABASE.NAME, null, DBConst.DATABASE.VERSION);
        this.context = context;
        if(!databaseExists() || DBConst.DATABASE.VERSION>getVersion())
        {
            synchronized(this)
            {
                boolean success = copyPrepopulatedDatabase();
                if(success)
                {
                    setVersion(DBConst.DATABASE.VERSION);
                }
            }
        }
    }

    private String getDBPath(){
        return "/data/data/" + context.getPackageName() + "/databases/";
    }

    private String getDBPathName(Context context){
        return "/data/data/" + context.getPackageName() + "/databases/" + DBConst.DATABASE.NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private boolean databaseExists()
    {
        File file = new File(getDBPath() + DBConst.DATABASE.NAME);
        return file.exists();
    }

    private boolean copyPrepopulatedDatabase()
    {
        // copy database from assets
        try
        {
            // create directories
            File dir = new File(getDBPath());
            dir.mkdirs();

            // output file name
            String outputFileName = getDBPathName(context);

            // create streams
            InputStream inputStream = context.getAssets().open(DBConst.DATABASE.NAME);
            OutputStream outputStream = new FileOutputStream(outputFileName);

            // write input to output
            byte[] buffer = new byte[1024];
            int length;
            while((length = inputStream.read(buffer))>0)
            {
                outputStream.write(buffer, 0, length);
            }

            // close streams
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            return true;
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    private int getVersion()
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(DBConst.DATABASE.PREFS_KEY_DATABASE_VERSION, 0);
    }


    private void setVersion(int version)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(DBConst.DATABASE.PREFS_KEY_DATABASE_VERSION, version);
        editor.apply();
    }
}
