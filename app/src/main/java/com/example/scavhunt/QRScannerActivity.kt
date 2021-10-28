package com.example.scavhunt

import android.app.Activity
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ScanMode
import kotlinx.android.synthetic.main.activity_qrscanner.*
import java.util.jar.Manifest

private const val CAMERA_REQUEST_CODE = 1 // Arbitrary, used for tracking multiple permissions

class QRScannerActivity : AppCompatActivity() {

    private var codeScanner: CodeScanner? = null
    private var result = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrscanner)

        if (checkPermissions()) {
            initializeCodeScanner()
            codeScanner?.startPreview()
        }

    }
    private fun checkPermissions() : Boolean {
        // Check required permissions and check if it is granted
        val permissions = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
        if (permissions != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this, arrayOf(android.Manifest.permission.CAMERA), CAMERA_REQUEST_CODE
            )
            return false
        }
        return true
    }

    private fun initializeCodeScanner() {
        val qrScannerView = findViewById<CodeScannerView>(R.id.qr_scanner_view)
        val qrScannerResult = findViewById<TextView>(R.id.qr_scanner_result)
        codeScanner = CodeScanner(this, qrScannerView)
        // Override default values
        codeScanner?.apply {
            formats = CodeScanner.TWO_DIMENSIONAL_FORMATS // Use QR codes not bar codes.
            scanMode = ScanMode.CONTINUOUS // Finds codes without manual user input
            // Code to execute upon QR code scanned
            decodeCallback = DecodeCallback {
                runOnUiThread {
                    result = it.text
                    qrScannerResult.text = result
                    //onBackPressed()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner?.startPreview()
    }

    override fun onPause() {
        codeScanner?.releaseResources()
        super.onPause()
    }

    override fun onBackPressed() {
        val returnIntent = intent.apply {
            putExtra("result", result)
        }
        setResult(Activity.RESULT_OK, returnIntent)
        super.onBackPressed()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initializeCodeScanner()
                    codeScanner?.startPreview()
                }
            }
        }
    }
}