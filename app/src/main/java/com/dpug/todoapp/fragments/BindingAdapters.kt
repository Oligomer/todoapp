package com.dpug.todoapp.fragments

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.dpug.todoapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BindingAdapters {

    companion object{
        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(view: FloatingActionButton, navigate: Boolean) {
                        view.setOnClickListener {
                if (navigate) {
                    view.findNavController().navigate(R.id.action_listFragment_to_addFragment)
                }
            }
        }

        @BindingAdapter("android:emptyDatabase")
        @JvmStatic
        fun emptyDatabase(view: View, emptyDatabase: MutableLiveData<Boolean>?) {
            emptyDatabase?.value?.let {
                view.isVisible = it
            }
        }
    }
}