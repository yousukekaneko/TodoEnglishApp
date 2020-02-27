package com.example.android.sample.todoenglishapp

import io.realm.RealmObject

open class TodoModel : RealmObject() {

    var title : String = ""
    var deadline : String = ""
    var taskDetail : String = ""
    var isCompleted : Boolean = false
}