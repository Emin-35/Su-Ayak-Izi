package com.example.semesterproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Patterns;
import android.widget.Toast;

public class DBHelper extends SQLiteOpenHelper {

    // Interface for updating the database simultaneously
    public interface InsertCallback {
        void onInsertComplete();
    }
    private static final String DATABASE_NAME = "LoginRegisterInformationDB";
    private static final int DATABASE_VERSION = 2;

    //----------------------------------------TABLES----------------------------------------
    // Table names
    private static final String TABLE_LOGIN = "loginTable";

    //--------------------------------------VALUES--------------------------------------
    // Define table and column names
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_NAME = "user_name";
    public static final String COLUMN_LASTNAME = "user_lastname";

    // Create TABLE_LOGIN
    private static final String CREATE_TABLE_LOGIN =
            "CREATE TABLE IF NOT EXISTS " + TABLE_LOGIN + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_EMAIL + " TEXT," +
                    COLUMN_PASSWORD + " TEXT," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_LASTNAME + " TEXT)";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the administrator table
        db.execSQL(CREATE_TABLE_LOGIN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade logic here if needed
    }

    // Checks if the given informations are correct
    public boolean checkLoginStatus(String email, String password) {
        // Open a readable database
        SQLiteDatabase db = this.getReadableDatabase();
        boolean status = false;

        // Define the columns to be retrieved from the database
        String[] columns = {COLUMN_EMAIL, COLUMN_PASSWORD};
        String selection = COLUMN_EMAIL + "=?";
        String[] selectionArgs = {email};

        // Execute a query on the database to retrieve data
        Cursor cursor = db.query(TABLE_LOGIN, columns, selection, selectionArgs, null, null, null);

        // Check if the cursor contains any rows (records) matching the query
        if (cursor.moveToFirst()) {

            int emailIndex = cursor.getColumnIndex(COLUMN_EMAIL);
            int passwordIndex = cursor.getColumnIndex(COLUMN_PASSWORD);

            // Check if the email and password is in the DB
            if (emailIndex != -1 && passwordIndex != -1) {
                String storedPassword = cursor.getString(passwordIndex);
                // If yes then make the status true
                if (password.equals(storedPassword)) {
                    status = true;
                }
            }
        }
        // Close the cursor to release its resources
        cursor.close();
        // Close the database
        db.close();
        // Return boolean value
        return status;
    }

    // Checks if the given e-mail is in the correct format
    public boolean isEmailValid(String email) {
        // Using Android's Patterns class to check if the email is in a valid format
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Method to insert email and password into the login table
    public void insertLoginData(String email, String password, Context context, InsertCallback callback) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);

        try {
            // Insert the values into the database
            db.insertOrThrow(TABLE_LOGIN, null, values);
            // Notify the callback that the insert operation is complete
            callback.onInsertComplete();
        }
        catch (SQLException e) {
            // Handle the exception appropriately (e.g., show a toast message)
            Toast.makeText(context, "Error inserting data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally {
            db.close();
        }
    }
}
