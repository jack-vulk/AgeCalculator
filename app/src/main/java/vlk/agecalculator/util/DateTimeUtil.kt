package vlk.agecalculator.util

import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

object DateTimeUtil {

    const val DEFAULT_FORMAT = "yyyy/MM/dd"

    fun getNow() : Date = Calendar.getInstance().time

    fun isValidDate(value : String ) : Boolean = isValidDate(DEFAULT_FORMAT, value)

    fun isValidDate(format : String, value : String) : Boolean = parse(format, value) != null

    fun parseValidDate(value : String) : Date = parseValidDate(DEFAULT_FORMAT, value)

    fun parseValidDate(format : String, value : String) : Date = parse(format, value)!!

    fun toLocalDate(input : Date) = input.toInstant().atZone(ZoneId.of("UTC")).toLocalDate()

    fun toString(value : Date) : String = toString(DEFAULT_FORMAT, value)

    fun toString(format : String, value : Date) : String {

        return try {
            SimpleDateFormat(format).format(value)
        } catch (e : Exception) {
            ""
        }

    }

    fun parse(value : String) : Date? = parse(DEFAULT_FORMAT, value)

    fun parse(format : String, value : String) : Date? {

        return try {
            SimpleDateFormat(format).parse(value)
        } catch (e : Exception) {
            null
        }
    }
}