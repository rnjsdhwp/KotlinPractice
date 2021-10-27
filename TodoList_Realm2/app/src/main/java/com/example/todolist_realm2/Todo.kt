package com.example.todolist_realm2

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Todo (
@PrimaryKey var id: Long = 0,
var title: String ="",
var number: String ="",
var address: String ="",
var date: Long = 0
) : RealmObject(){
}