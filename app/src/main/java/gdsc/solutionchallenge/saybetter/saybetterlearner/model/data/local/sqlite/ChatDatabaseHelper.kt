package gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.sqlite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

import android.database.sqlite.SQLiteOpenHelper
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.entity.ChatMessage


class ChatDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(TABLE_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "chat.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "chat"
        const val ID = "id"
        const val CHAT_SENDER = "sender"
        const val CHAT_MESSAGE = "message"
        private const val TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CHAT_SENDER + " INTEGER, " +
                CHAT_MESSAGE + " TEXT);"
    }

    fun addChat(message : ChatMessage){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(CHAT_MESSAGE, message.message)
        values.put(CHAT_SENDER, if (message.isUser) 1 else 0)

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    @SuppressLint("Range")
    fun getAllChats(): List<ChatMessage> {
        val chats = ArrayList<ChatMessage>()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val chat = ChatMessage(
                    message = cursor.getString(cursor.getColumnIndex(CHAT_MESSAGE)),
                    isUser = cursor.getInt(cursor.getColumnIndex(CHAT_SENDER)) == 1,
                    symbol = 0
                )
                chats.add(chat)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return chats
    }
}

