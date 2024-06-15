package com.ratan.maigen.view.activity

import TFLiteModelHelper
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ratan.maigen.R

class SurveyPreferenceActivity : AppCompatActivity() {

    private lateinit var checkBoxAgrowisata: CheckBox
    private lateinit var checkBoxAlam: CheckBox
    private lateinit var checkBoxBelanja: CheckBox
    private lateinit var checkBoxBudaya: CheckBox
    private lateinit var checkBoxCagarAlam: CheckBox
    private lateinit var checkBoxPantai: CheckBox
    private lateinit var checkBoxRekreasi: CheckBox
    private lateinit var checkBoxReligius: CheckBox
    private lateinit var buttonSubmit: Button
    private lateinit var textViewResult: TextView

    private lateinit var modelHelper: TFLiteModelHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey_preference)

        checkBoxAgrowisata = findViewById(R.id.checkBoxAgrowisata)
        checkBoxAlam = findViewById(R.id.checkBoxAlam)
        checkBoxBelanja = findViewById(R.id.checkBoxBelanja)
        checkBoxBudaya = findViewById(R.id.checkBoxBudaya)
        checkBoxCagarAlam = findViewById(R.id.checkBoxCagarAlam)
        checkBoxPantai = findViewById(R.id.checkBoxPantai)
        checkBoxRekreasi = findViewById(R.id.checkBoxRekreasi)
        checkBoxReligius = findViewById(R.id.checkBoxReligius)
        buttonSubmit = findViewById(R.id.buttonSubmit)
        textViewResult = findViewById(R.id.textViewResult)


        buttonSubmit.setOnClickListener {
            val input = FloatArray(4)
            input[0] = if (checkBoxAgrowisata.isChecked) 1.0f else 0.0f
            input[1] = if (checkBoxAlam.isChecked) 1.0f else 0.0f
            input[2] = if (checkBoxBelanja.isChecked) 1.0f else 0.0f
            input[3] = if (checkBoxBudaya.isChecked) 1.0f else 0.0f
            input[4] = if (checkBoxCagarAlam.isChecked) 1.0f else 0.0f
            input[5] = if (checkBoxPantai.isChecked) 1.0f else 0.0f
            input[6] = if (checkBoxRekreasi.isChecked) 1.0f else 0.0f
            input[7] = if (checkBoxReligius.isChecked) 1.0f else 0.0f
        }

        val button: Button = findViewById(R.id.buttonSubmit)

        button.setOnClickListener{
            val input = FloatArray(65)

            val modelHelper = TFLiteModelHelper(this, onError = { error ->
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
            })
            val prediction = modelHelper.predict(input)

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("prediction",prediction)
            startActivity(intent)

            modelHelper.close()
        }
    }

}