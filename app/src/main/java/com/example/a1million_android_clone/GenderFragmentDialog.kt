package com.example.a1million_android_clone

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_dialog_gender.view.*

class GenderFragmentDialog : DialogFragment() {

    private var listener: GenderFragmentDialog.OnGenderFragmentInteractionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_dialog_gender, container)

        v.gender_cancel_button.setOnClickListener {
            dismiss()
        }

        v.gender_confirm_button.setOnClickListener {

            if (v.gender_radio_group.checkedRadioButtonId == -1) {
                dismiss()
            } else {
                listener?.onGenderFragmentInteraction(v.findViewById<RadioButton>(v.gender_radio_group.checkedRadioButtonId).text.toString())
                dismiss()
            }
        }

        return v
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnGenderFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnGenderFragmentInteractionListener")
        }
    }

    interface OnGenderFragmentInteractionListener {
        fun onGenderFragmentInteraction(msg: String)
    }

}