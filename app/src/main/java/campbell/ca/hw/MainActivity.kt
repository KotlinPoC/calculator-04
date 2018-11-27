package campbell.ca.hw

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView

/**
 * Simple calculator, two entry fields on the ui
 * A button for each of add, subtract, multiply and divide
 * which generate a result
 *
 * Minor data validation (not empty, no divide by zero)
 *
 * This version maintains state for the result
 *
 * @author PMCampbell
 * @version 4
 *
 * Changes:
 * add french strings & image for UI (no changes to code)
 * make the image a button that launches google maps (as lab 4)
 * remove inline string for result
 */
class MainActivity : AppCompatActivity() {
    internal val TAG = "CALC"  // tag for Logging
    internal var etNum1: EditText? = null
    internal var etNum2: EditText? = null
    internal var result: TextView? = null
    internal var num1: Double = 0.toDouble()
    internal var num2: Double = 0.toDouble()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // get a handle to text fields
        etNum1 = findViewById(R.id.num1) as EditText
        etNum2 = findViewById(R.id.num2) as EditText
        result = findViewById(R.id.result) as TextView
    }

    fun addNums(v: View) {
        if (readNums())
            result!!.text = java.lang.Double.toString(num1 + num2)
    }

    fun subtrNums(v: View) {
        if (readNums())
            result!!.text = java.lang.Double.toString(num1 - num2)
    }

    fun divNums(v: View) {
        if (!readNums())
            return
        if (num2 == 0.0)
            result!!.text = "Cannot divide by 0"
        else
            result!!.text = java.lang.Double.toString(num1 / num2)
    }

    fun multNums(v: View) {
        if (readNums())
            result!!.text = java.lang.Double.toString(num1 * num2)
    }

    fun readNums(): Boolean {
        if (etNum1!!.text.toString().isEmpty() || etNum2!!.text.toString().isEmpty()) {
            result!!.text = "Number(s) input invalid"
            return false
        }
        // TODO should be checking this ...
        num1 = java.lang.Double.parseDouble(etNum1!!.text.toString())
        num2 = java.lang.Double.parseDouble(etNum2!!.text.toString())
        return true
    }

    /**
     * State method onSaveInstanceState
     * we don't need to keep the state of EditText etc if we use them,
     * all Views with an id are saved by the superclass in
     * the instance bundle automatically by Android
     * if onSaveInstanceState() is called it will be run before onStop()
     *
     * For this app the only thing we need to save ourselves is the result
     * which is in a TextView the EditText and Buttons are saved by Android
     * when we call the super.
     *
     * @param outState
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        var strResult = "not set"
        if (result != null) {
            strResult = result!!.text.toString()
        }

        outState.putString("result", strResult)
        Log.d(TAG, "onSaveInstanceState() result:$strResult")

    }

    /**
     * State method onSaveInstanceState
     * restore savedInstanceState here or in onCreate(Bundle)
     *
     * For this app the only thing we need to restore ourselves is the result
     * which is in a TextView the EditText and Buttons are restored by Android
     * when we call the super.
     *
     *
     * @param inState   state bundle
     */
    @Override
    override fun onRestoreInstanceState(inState: Bundle) {
        super.onRestoreInstanceState(inState)
        // restore savedInstanceState here or in onCreate(Bundle)
        // Check whether we're recreating a previously destroyed instance
        if (inState != null) {
            with(inState) {
                // Restore value of member(s) from saved state
                result!!.text = getString("result")
            }

            Log.d(TAG, "onRestoreInstanceState() result:" + result!!.text)
        }
    }

        /**
         * Button for image, launches activity that
         * has an intent-filter that responds to
         * implicit intent:  view + geo uri
         * @param v
         */
        fun showMap(v: View) {

            val country = getResources().getString(R.string.country)
            val geoLocation = Uri.parse("geo:0,0?q=" + Uri.encode(country))

            val geoIntent = Intent(Intent.ACTION_VIEW)

            geoIntent.data = geoLocation
            if (geoIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(geoIntent)
            } else {
                result!!.setText(R.string.error_no_geo)
            }

        }
}