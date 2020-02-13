package com.example.android.sample.todoenglishapp

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.text.SimpleDateFormat
import java.util.*

class DatePickerDialogFragment: DialogFragment(), DatePickerDialog.OnDateSetListener {

    var listener: OnDateSetListner? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnDateSetListner) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnDateSetListner {
        fun onDateSelected()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)


        return DatePickerDialog(activity, this, year, month, day)
    }

    override fun onDateSet(datePicker: DatePicker?, year: Int, month: Int, day: Int) {
        val dateString = getDateString(year, month, day)
    }

    private fun getDateString(year: Int, month: Int, day: Int): String {
        val calender = Calendar.getInstance()
        calender.set(year, month, day)
        val dateFormat = SimpleDateFormat("yyyy/MM/dd")
        return dateFormat.format(calender.time)
    }
}