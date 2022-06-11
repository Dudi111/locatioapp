package com.example.locationapp.frag

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.locationapp.MainActivity
import com.example.locationapp.R
import kotlinx.android.synthetic.main.fragment_splash.*


class SplashFrag : Fragment() {

    companion object{

        lateinit var mypref: mypref
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    mypref= mypref((requireContext()))





        SplashLogo.setMaxProgress(1f)
        SplashLogo.playAnimation()


        Handler().postDelayed({

            if(mypref.isLoggedIn){
                val fragManager= requireActivity().supportFragmentManager
                val trasaction= fragManager?.beginTransaction()
                val loginfrag= LocationFrag()
                trasaction!!.replace(R.id.container,loginfrag).commit()
            }else {
                val fragManager = requireActivity().supportFragmentManager
                val trasaction = fragManager?.beginTransaction()
                val loginfrag = LoginFrag()
                trasaction!!.replace(R.id.container, loginfrag).commit()
            }



        },2500)




    }


}