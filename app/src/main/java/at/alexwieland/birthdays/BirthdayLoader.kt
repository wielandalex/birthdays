package at.alexwieland.birthdays

import android.content.Context
import android.provider.ContactsContract
import at.alexwieland.birthdays.models.Birthday
import java.time.LocalDate

class BirthdayLoader {
    private val mProjection: Array<String> = arrayOf(
        ContactsContract.Contacts._ID,
        ContactsContract.Contacts.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Event.START_DATE
    )

    private val mSelection: String? =
        "${ContactsContract.Data.MIMETYPE} = ? AND " +
                "${ContactsContract.CommonDataKinds.Event.TYPE} = " +
                "${ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY}"

    private val mSelectionArgs: Array<String>? = arrayOf(
        ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE
    )

    fun getBirthdays(context: Context): Array<Birthday?> {
        val cursor = context.contentResolver.query(
            ContactsContract.Data.CONTENT_URI,
            mProjection,
            mSelection,
            mSelectionArgs,
            null
        ) ?: return emptyArray()


        val birthdays = arrayOfNulls<Birthday>(cursor.count)

        cursor.apply {
            val idIndex = getColumnIndex(ContactsContract.Contacts._ID)
            val nameIndex = getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            val dateIndex = getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE)

            while (moveToNext()) {
                val id = getInt(idIndex)
                val name = getString(nameIndex)
                var dateString = getString(dateIndex)
                dateString = sanitizeDateString(dateString)
                val date = LocalDate.parse(dateString)

                birthdays[cursor.position] = Birthday(id, name, date)
            }

            close()
        }

        return birthdays
    }

    private fun sanitizeDateString(dateString: String): String {
        var sanitizedString = dateString
        val now = LocalDate.now()

        if (dateString.substring(0, 1) == "-") {
            sanitizedString = dateString.replaceFirst("-", now.year.toString())
        }

        return sanitizedString
    }
}