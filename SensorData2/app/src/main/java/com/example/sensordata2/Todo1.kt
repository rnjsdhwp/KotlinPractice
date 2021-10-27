package com.example.sensordata2

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Todo1 (
    @PrimaryKey var id: Long = 0,
    var name: String ="",
    var power: String = "",
    var res: String = "",
    var range: String = ""
//    var power: Float = 0f,
//    var res: Float = 0f,
//    var range: Float = 0f
) : RealmObject(){
}