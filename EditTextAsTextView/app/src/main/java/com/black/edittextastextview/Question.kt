package com.black.edittextastextview

import java.io.Serializable

data class Question(
    var question: String,
    var answer:String): Serializable {
    constructor() : this("", "") {}
}
