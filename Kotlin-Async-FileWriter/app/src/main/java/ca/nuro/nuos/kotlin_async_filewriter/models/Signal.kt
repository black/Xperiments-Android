package ca.nuro.nuos.kotlin_async_filewriter.models

class Signal(private val data: String) {
    fun toCSVData(): String {
        val time: Long = System.currentTimeMillis()
        return "$time,$data\n"
    }
}
