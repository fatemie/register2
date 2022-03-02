package com.example.register2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.register2.databinding.ActivityShowBinding

const val NEWUSER = "NEW USER"

class ShowActivity : AppCompatActivity() {
    lateinit var binding : ActivityShowBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        setInfo()
        binding.buttonEdit.setOnClickListener {
            goBackForEdit()
        }
        binding.button.setOnClickListener {
            goBackForEnterNewUser()
        }
    }

    fun setInfo(){
        var prefs = getSharedPreferences(resources.getString(R.string.app_name), MODE_PRIVATE)
        binding.textViewNationalCode.text = "کدملی: " + prefs.getString(NATIONALCODE,"")
        binding.textViewBornCity.text = "محل تولد: " + prefs.getString(BORNCITY,"")
        binding.textViewAddress.text = "آدرس: " + prefs.getString(ADDRESS,"")
        binding.textViewPostalCode.text = "کد پستی: " + prefs.getString(POSTALCODE,"")
        binding.textViewGender.text = "جنسیت: " + prefs.getString(GENDER,"")
    }

    fun goBackForEdit(){
        val result= Intent()
        setResult(RESULT_OK,result)
        finish()
    }

    fun goBackForEnterNewUser(){
        val result= Intent()
        result.putExtra(NEWUSER, true)
        setResult(RESULT_OK,result)
        finish()
    }

}