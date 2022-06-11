package com.example.locationapp.frag

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.locationapp.MainActivity
import com.example.locationapp.R
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFrag : Fragment() {

    companion object{
        lateinit var mypref:mypref
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mypref= mypref(requireContext())



        btn_proceed.setOnClickListener {



            mypref.setname(et_name.text.toString())
            mypref.setnumber(etnumbere.text.toString())
            mypref.loginInfo(true)
            val fragManager= activity?.supportFragmentManager
            val trasaction= fragManager?.beginTransaction()
            val locationfrag= LocationFrag()
            trasaction!!.replace(R.id.container,locationfrag).commit()


        }
    }


}