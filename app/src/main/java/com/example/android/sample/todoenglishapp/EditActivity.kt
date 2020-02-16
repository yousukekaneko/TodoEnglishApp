package com.example.android.sample.todoenglishapp

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity(), EditFragment.OnFragmentInteractionListener,
    DatePickerDialogFragment.OnDateSetListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setSupportActionBar(toolbar)

        toolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
            setNavigationOnClickListener{
                finish()
            }
        }

        val bundle = intent.extras
        val title = bundle.getString(IntentKey.TITLE.name)
        val deadline = bundle.getString(IntentKey.DEADLINE.name)
        val taskDetail = bundle.getString(IntentKey.TASK_DETAIL.name)
        val isCompleted = bundle.getBoolean(IntentKey.IS_COMPLETED.name)
        val mode = bundle?.getSerializable(IntentKey.MODE_IN_EDIT.name) as ModeInEdit

        supportFragmentManager.beginTransaction()
            .add(R.id.container_detail,
                EditFragment.newInstance(title, deadline, taskDetail, isCompleted, mode),
                FragmentTag.EDIT.toString()).commit()

        return
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        menu!!.apply {
            findItem(R.id.menu_delete).isVisible = false
            findItem(R.id.menu_edit).isVisible = false
            findItem(R.id.menu_register).isVisible = false
        }
        return true
    }

    override fun onDatePickerLaunched() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDateSelected() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
