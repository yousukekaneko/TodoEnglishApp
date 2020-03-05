package com.example.android.sample.todoenglishapp

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_detail.*

private val ARG_title = IntentKey.TITLE.name
private val ARG_deadline = IntentKey.DEAD_LINE.name
private val ARG_taskDetail = IntentKey.TASK_DETAIL.name
private val ARG_isCompleted = IntentKey.IS_COMPLETED.name

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [DetailFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {

    private var title: String? = null
    private var deadline: String? = null
    private var taskDetail: String? = null
    private var isCompleted: Boolean? = null

    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            arguments?.let {
                title = it.getString(ARG_title)
                deadline = it.getString(ARG_deadline)
                taskDetail = it.getString(ARG_taskDetail)
                isCompleted = it.getBoolean(ARG_isCompleted)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        title_detail.text = title
        deadline_detail.text = deadline
        todo_detail.text = taskDetail
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        menu!!.apply {
            findItem(R.id.menu_delete).isVisible = true
            findItem(R.id.menu_edit).isVisible = true
            findItem(R.id.menu_register).isVisible = true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.menu_delete -> {
                deleteSelectedTodo(title, deadline, taskDetail)
            }
            R.id.menu_edit -> {
                listener?.onEditSelectedTodo(title!!, deadline!!, taskDetail!!, isCompleted!!, ModeInEdit.EDIT)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteSelectedTodo(title: String?, deadline: String?, taskDetail: String?) {
        val realm = Realm.getDefaultInstance()
        val selectedTodo = realm.where(TodoModel::class.java)
            .equalTo(TodoModel::title.name,title)
            .equalTo(TodoModel::deadline.name,deadline)
            .equalTo(TodoModel::taskDetail.name, taskDetail)
            .findFirst()
        realm.beginTransaction()
        selectedTodo.deleteFromRealm()
        realm.commitTransaction()

        listener?.onDataDeleted()
        fragmentManager!!.beginTransaction().remove(this).commit()
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
        fun onDataDeleted()
        fun onEditSelectedTodo(title: String, deadline: String, taskDetail: String,
                               isCompleted: Boolean, mode: ModeInEdit)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailFragment.
         */

        @JvmStatic
        fun newInstance(title: String, deadline: String, taskDetail: String, isCompleted: Boolean) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_title, title)
                    putString(ARG_deadline, deadline)
                    putString(ARG_taskDetail, taskDetail)
                    putBoolean(ARG_isCompleted, isCompleted)
                }
            }
    }
}
