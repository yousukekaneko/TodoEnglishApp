package com.example.android.sample.todoenglishapp

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_edit.*
import java.io.Serializable

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private val ARG_title = IntentKey.TITLE.name
private val ARG_deadLine = IntentKey.DEADLINE.name
private val ARG_taskDetail = IntentKey.TASK_DETAIL.name
private val ARG_isCompleted = IntentKey.IS_COMPLETED.name
private val ARG_mode = IntentKey.MODE_IN_EDIT.name


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [EditFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [EditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var title: String? = null
    private var deadLine: String? = null
    private var taskDetail: String? = null
    private var isCompleted: Boolean? = null
    private var mode: Serializable? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            arguments?.let {
                title = it.getString(ARG_title)
                deadLine = it.getString(ARG_deadLine)
                taskDetail = it.getString(ARG_taskDetail)
                isCompleted = it.getBoolean(ARG_isCompleted)
                mode = it.getSerializable(ARG_mode) as ModeInEdit
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit, container, false)
            setHasOptionsMenu(true)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateUi(mode!! as ModeInEdit)
        imageButtonDateSet.setOnClickListener {
            listener!!.onDatePickerLaunched()
        }
    }

    private fun updateUi(mode: ModeInEdit) {
        when (mode) {
            ModeInEdit.NEW_ENTRY -> {

            }
            ModeInEdit.EDIT -> {
                checkBox.visibility = View.INVISIBLE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        menu!!.apply {
            findItem(R.id.menu_delete).isVisible = false
            findItem(R.id.menu_edit).isVisible = false
            findItem(R.id.menu_register).isVisible = true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.menu_register) recordToRealmDB(mode)
        return super.onOptionsItemSelected(item)
    }

    private fun recordToRealmDB(mode: ModeInEdit?) {
        when (mode) {
            ModeInEdit.NEW_ENTRY -> addNewTodo()
            ModeInEdit.EDIT -> editExistTodo()
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onDatePickerLaunched()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(title: String, deadLine: String, taskDetail: String, isCompleted: Boolean, mode: ModeInEdit) =
            EditFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_title, title)
                    putString(ARG_deadLine, deadLine)
                    putString(ARG_taskDetail, taskDetail)
                    putBoolean(ARG_isCompleted, isCompleted)
                    putSerializable(ARG_mode, mode)

                }
            }
    }
}
