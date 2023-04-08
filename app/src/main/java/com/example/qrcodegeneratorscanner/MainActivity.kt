package com.example.qrcodegeneratorscanner

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.qrcodegeneratorscanner.databinding.ActivityMainBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
//    val inputText = binding.editText.text.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bScan.setOnClickListener {
            CheckCameraPermission()
        }


        binding.buttongenerate.setOnClickListener {


            try {
                val barcodeEncode: BarcodeEncoder = BarcodeEncoder()
                val bitmap: Bitmap = barcodeEncode.encodeBitmap(
                    binding.editText.getText().toString(),
                    BarcodeFormat.QR_CODE,
                    750,
                    750
                )
                binding.imageView.setImageBitmap(bitmap)
            } catch (e: WriterException) {
                Toast.makeText(this, R.string.succes, Toast.LENGTH_SHORT).show()

            }


        }

    }

    fun CheckCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA), 12
            )

        } else {
            startActivity(Intent(this, ScannerActivity::class.java))
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 12) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivity(Intent(this, ScannerActivity::class.java))
            }
        }
    }

}