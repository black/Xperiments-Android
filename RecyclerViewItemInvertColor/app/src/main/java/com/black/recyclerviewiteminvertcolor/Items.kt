package com.black.recyclerviewiteminvertcolor

data class Items(
    var title: String,
    var description: String,
    var img: String,
){
    constructor() : this("", "", "") { }
}