package com.example.register2

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.register2.databinding.ActivityMainBinding

const val NAME = "NAME"
const val NATIONALCODE = "NATIONALCODE"
const val BORNCITY = "BORNCITY"
const val ADDRESS = "ADDRESS"
const val POSTALCODE = "POSTALCODE"
const val GENDER = "GENDER"

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    lateinit var prefs : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        prefs = getSharedPreferences(resources.getString(R.string.app_name), MODE_PRIVATE)
        setListener()
    }

    private fun setListener() {
        binding.buttonRegister.setOnClickListener {
            if(checkAllFieldComplete()){
                if (checkAllFieldCorrectComplete()){
                    seveInfo()
                    goToShowInfoActivity()
                }
            }
        }
    }

    private fun checkAllFieldComplete() :Boolean{
        val gender = when(binding.gender.checkedRadioButtonId){
            binding.female.id -> "Female"
            binding.male.id -> "Male"
            else -> "not choose!"
        }
        if(gender == "not choose!"){
            Toast.makeText(this, "جنسیت را انتخاب کنید", Toast.LENGTH_SHORT).show()
        }

        checkFieldComplete(binding.editTextPersonName)
        checkFieldComplete(binding.editTextPersonNationalCode)
        checkFieldComplete(binding.editTextBornCity)
        checkFieldComplete(binding.editTextAddress)
        checkFieldComplete(binding.editTextPostalCode)

        if(checkFieldComplete(binding.editTextPersonName) &&
        checkFieldComplete(binding.editTextPersonNationalCode) &&
        checkFieldComplete(binding.editTextBornCity) &&
        checkFieldComplete(binding.editTextAddress) &&
        checkFieldComplete(binding.editTextPostalCode)){
            return true
        }
        return false
    }

    fun checkFieldComplete(editText: EditText): Boolean{
        if(editText.text.length == 0){
            editText.setError("لطفا این قسمت را تکمیل کنید")
            return false
        }
        return true
    }

    private fun checkAllFieldCorrectComplete(): Boolean {
        val nationalCode = binding.editTextPersonNationalCode.text
        if(nationalCode.length != 10){
            binding.editTextPersonNationalCode.setError("کد ملی باید 10 رقم داشته باشد")
            return false
        }
        val postalCode = binding.editTextPostalCode.text
        if(postalCode.length != 10){
            binding.editTextPostalCode.setError("کد پستی باید 10 رقم داشته باشد")
            return false
        }
        return true
    }

    private fun seveInfo() {
        val editor =  prefs.edit()
        editor.putString(NAME, binding.editTextPersonName.text.toString())
        editor.putString(NATIONALCODE, binding.editTextPersonNationalCode.text.toString())
        editor.putString(BORNCITY, binding.editTextBornCity.text.toString())
        editor.putString(ADDRESS, binding.editTextAddress.text.toString())
        editor.putString(POSTALCODE, binding.editTextPostalCode.text.toString())
        val gender = when(binding.gender.checkedRadioButtonId){
            binding.female.id -> "زن"
            binding.male.id -> "مرد"
            else -> "not choose!"
        }
        editor.putString(GENDER, gender)
        editor.apply()
    }

    fun goToShowInfoActivity(){
        intent = Intent(this, ShowActivity::class.java)
        startForResult.launch(intent)
    }

    val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                val isNewUser =  intent?.getBooleanExtra(NEWUSER, false)
                if(isNewUser!!){
                    binding.editTextPersonName.text.clear()
                    binding.editTextPersonNationalCode.text.clear()
                    binding.editTextBornCity.text.clear()
                    binding.editTextAddress.text.clear()
                    binding.editTextPostalCode.text.clear()
                    binding.gender.clearCheck()
                }

            }

        }

}