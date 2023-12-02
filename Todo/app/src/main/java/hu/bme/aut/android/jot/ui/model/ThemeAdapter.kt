package hu.bme.aut.android.jot.ui.model

public class ThemeAdapter {
    private var instance: ThemeAdapter? = null

    private fun Singleton() {}

    fun getInstance(): ThemeAdapter? {
        if (instance == null) {
            synchronized(ThemeAdapter::class.java) {
                if (instance == null) {
                    instance = ThemeAdapter()
                }
            }
        }
        return instance
    }



}