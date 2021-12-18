package com.black.xperiments.cardsoflogic.logic

import java.io.Serializable

data class Cards(
    var imgSrc:String?=null,
    var title:String?="Name of the Fallacy",
    var explanation:String?="Here is the explanation of the fallacy",
    var example:String?="Example of the fallacy",
    var conclusion:String?="Conclusion of the example"): Serializable {
    constructor() : this("", "", "", "", "") {}
}
