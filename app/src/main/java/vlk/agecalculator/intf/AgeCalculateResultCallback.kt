package vlk.agecalculator.intf

interface AgeCalculateResultCallback {

    fun onSuccess(timeGap : Long)

    fun onError(errorMsg : String)
}