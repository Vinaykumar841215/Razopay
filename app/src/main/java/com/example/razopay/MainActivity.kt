package com.example.razopay

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity(),PaymentResultListener {

    lateinit var amtEdt: EditText
    lateinit var payBtn: AppCompatButton
    lateinit var save : TextView

    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        amtEdt = findViewById(R.id.amountEdit)
        payBtn = findViewById(R.id.paymentBtn)
        save = findViewById(R.id.save)


 // on below line adding click listener for pay button
           payBtn.setOnClickListener {

                // on below line getting amount from edit text
                val amt = amtEdt.text.toString()

                // rounding off the amount.
                val amount = Math.round(amt.toFloat() * 100).toInt()

                // on below line we are
                // initializing razorpay account
                val checkout = Checkout()

                // on the below line we have to see our id.
                checkout.setKeyID("rzp_test_6Z07X53EN5P7Cx")

                // set image
                checkout.setImage(R.drawable.ic_launcher_background)

                // initialize json object
                val obj = JSONObject()
                try {
                     obj.put("name", "EduGaon")
                    obj.put("description", "Test payment")
                    obj.put("theme.color", "@color/blue")
                    obj.put("currency", "INR")
                    obj.put("amount", amount)


                    //put the prefilled information

                    val prefill = JSONObject()
                    prefill.put("prefill.contact", "7488384463")
                    prefill.put("email", "vinayraj758838@gmail.com")
                     obj.put("pefill",prefill)
                    // open razorpay to checkout activity
                   checkout.open(this@MainActivity, obj)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        }

    override fun onPaymentSuccess(s: String?) {
        // this method is called on payment success.

        Toast.makeText(this, "Payment is successful : " + s, Toast.LENGTH_SHORT).show()
        save.text = s
        save.setTextColor(Color.BLUE)
    }

    override fun onPaymentError(p0: Int, s: String?) {
        // on payment failed.
        Toast.makeText(this, "Payment Failed due to error : " + s, Toast.LENGTH_SHORT).show();
    }


}
