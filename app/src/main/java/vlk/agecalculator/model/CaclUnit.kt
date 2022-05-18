package vlk.agecalculator.model

enum class CaclUnit (private val value : String) {

    Year("Year"),
    Month("Month"),
    Day("Day"),
    Hour("Hour"),
    Minute("Minute"),
    Second("Second");

    override fun toString() : String {
        return value;
    }

}