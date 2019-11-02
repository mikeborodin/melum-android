package app.melum.common


object Strings {

    @JvmStatic
    fun cutLongText(s: String, limit: Int) = s.let {
        if (it.length > limit) s.substring(0 until limit) + "..." else s
    }

}