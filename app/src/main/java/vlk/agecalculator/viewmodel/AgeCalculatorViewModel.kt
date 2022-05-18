package vlk.agecalculator.viewmodel

import androidx.lifecycle.ViewModel
import vlk.agecalculator.intf.AgeCalculateResultCallback
import vlk.agecalculator.model.CaclUnit
import vlk.agecalculator.model.Input
import vlk.agecalculator.util.DateTimeUtil
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.concurrent.TimeUnit

class AgeCalculatorViewModel(private val listener : AgeCalculateResultCallback) : ViewModel() {

    /**
     * Calculate time gap from dob
     * @param input
     */
    fun calculate(input : Input) {

        // check for invalid input
        if (!DateTimeUtil.isValidDate(input.dob)) {
            this.listener.onError("Invalid Date")
            return
        }

        // for valid input,
        // calculate time gap
        val dDob = DateTimeUtil.parseValidDate(input.dob)
        this.listener.onSuccess(calculate(dDob, input.unit))
    }

    /**
     * Calculate time gap
     * @param dob
     * @param unit
     * @return timegap
     */
    private fun calculate(dob : Date, unit : CaclUnit) : Long {

        val now = DateTimeUtil.getNow()
        val diffInMilisec = now.time - dob.time

        return when (unit) {

            CaclUnit.Year -> ChronoUnit.YEARS.between(DateTimeUtil.toLocalDate(dob), DateTimeUtil.toLocalDate(now))
            CaclUnit.Month -> ChronoUnit.MONTHS.between(DateTimeUtil.toLocalDate(dob), DateTimeUtil.toLocalDate(now))
            CaclUnit.Day -> TimeUnit.MILLISECONDS.toDays(diffInMilisec)
            CaclUnit.Hour -> TimeUnit.MILLISECONDS.toHours(diffInMilisec)
            CaclUnit.Minute -> TimeUnit.MILLISECONDS.toMinutes(diffInMilisec)
            CaclUnit.Second -> TimeUnit.MILLISECONDS.toSeconds(diffInMilisec)
        }

    }



}