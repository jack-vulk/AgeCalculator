package vlk.agecalculator

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import org.w3c.dom.Text
import vlk.agecalculator.databinding.ActivityMainBinding
import vlk.agecalculator.intf.AgeCalculateResultCallback
import vlk.agecalculator.model.CaclUnit
import vlk.agecalculator.model.Input
import vlk.agecalculator.util.DateTimeUtil
import vlk.agecalculator.viewmodel.AgeCalculatorViewModel
import java.util.*

class MainActivity : AppCompatActivity(), AgeCalculateResultCallback {

    private lateinit var binding: ActivityMainBinding
    private val model : AgeCalculatorViewModel = AgeCalculatorViewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initDteInput()
        initDteUnit()
        initBtn()
    }

    /**
     *
     */
    private fun initDteInput() {

        val txtDte = this.getInputDob()

        txtDte.setOnClickListener {
            val inputtedCalendar = Calendar.getInstance()
            // Re-use inputted date as init value
            // if there isn't any inputted date, use current datetime
            inputtedCalendar.time = DateTimeUtil.parse(txtDte.text.toString()) ?: DateTimeUtil.getNow()

            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, month, day ->
                    run {
                        // convert select year, month, day to yyyy/mm/dd
                        // and set it into input field
                        txtDte.setText(toYMDWithSplash(year, month + 1, day))
                    }
                },
                inputtedCalendar.get(Calendar.YEAR),
                inputtedCalendar.get(Calendar.MONTH),
                inputtedCalendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

    }

    /**
     *
     */
    private fun initDteUnit() {
        this.getInputUnit().adapter = ArrayAdapter<CaclUnit>(this, android.R.layout.simple_list_item_1, CaclUnit.values())
    }

    private fun initBtn() {
        this.getBtnAction().setOnClickListener { model.calculate(this.makeInput()) }
    }

    // ==================
    // Other events
    // ==================

    override fun onPause() {
        super.onPause()
        this.toggleResultLayout(false)
    }

    // ===================
    // Callback for AgeCalculatorViewModel
    // ===================
    /**
     * Callback on success
     * @param timeGap calculated timegap
     */
    override fun onSuccess(timeGap: Long) {
        this.showCaclResult(timeGap)
    }

    /**
     * Callback on error
     * @param errorMsg returned error message
     */
    override fun onError(errorMsg: String) {
        this.getInputDob().error = errorMsg
    }

    // ===================
    // I/O Method
    // ===================
    /**
     * Make input for AgeCalculatorViewModel
     * @return input for AgeCalculatorViewModel
     */
    private fun makeInput() : Input {

        val dob : String = this.getInputDob().text.toString()
        val unit : CaclUnit = this.getInputUnit().selectedItem as CaclUnit

        return Input(dob, unit)

    }

    /**
     * Show calculated time gap
     * @param timeGap time gap calculated by AgeCalculatgorViewModel
     */
    private fun showCaclResult(timeGap: Long) {

        this.toggleResultLayout(true)
        // Show the result
        this.getOutputResultMsg().text = "You have lived for $timeGap"

    }

    /**
     * Toggle Result Layout
     * @param toShow
     */
    private fun toggleResultLayout(toShow : Boolean) {
        this.findViewById<LinearLayout>(R.id.lResult).visibility = if (toShow) View.VISIBLE else View.GONE
    }

    // ==================
    // Getter for UI component
    // ==================
    /**
     * Input.DOB
     * @return reference to input.dob
     */
    private fun getInputDob() : TextInputEditText = this.findViewById<TextInputEditText>(R.id.inTextDob)

    /**
     * Input.Unit
     * @return reference to input.unit
     */
    private fun getInputUnit() : Spinner = this.findViewById<Spinner>(R.id.lstDayUnits)

    /**
     * Btn.Action
     * @return reference to button.Action
     */
    private fun getBtnAction() : Button = this.findViewById<Button>(R.id.btnAction)

    /**
     * Output.ResultMsg
     * @return reference to Output.ResultMsg
     */
    private fun getOutputResultMsg() : TextView = this.findViewById<TextView>(R.id.txtResult)

    // ==================
    // Utility
    // ==================
    companion object {

        /**
         * @param year
         * @param month
         * @param day
         * @return yyyy/mm/dd
         */
        fun toYMDWithSplash(year : Int, month : Int, day : Int) : String = "$year/$month/$day"
    }
}