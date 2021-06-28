package es.jnsoft.movieme.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import com.google.android.material.snackbar.Snackbar

abstract class BaseDetailFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    abstract fun setupBindings()

    fun showSnackBar(error: String) {
        Snackbar.make(this.requireView(), error, Snackbar.LENGTH_LONG).show()
    }
}