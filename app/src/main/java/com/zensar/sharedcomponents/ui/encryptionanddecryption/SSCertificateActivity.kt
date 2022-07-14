package com.zensar.sharedcomponents.ui.encryptionanddecryption

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.android.appcomponents.util.SSLCertificate
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.NoCache
import com.android.volley.toolbox.StringRequest
import com.zensar.sharedcomponents.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Request
import okio.ByteString.Companion.decodeBase64
import okio.ByteString.Companion.toByteString
import java.net.URL
import java.security.cert.Certificate
import javax.net.ssl.HttpsURLConnection

const val DIGICERT_ROOT_SHA256 = "5kJvNEMw0KjrCAu7eXY5HZdvyCS13BbA0VJG1RSP91w="

class SSCertificateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sslcertificate_main)
    }

    private fun onStart(@IdRes id: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            val button = findViewById<Button>(id)
            button.setBackgroundColor(
                ContextCompat.getColor(this@SSCertificateActivity, R.color.purple_500)
            )
            button.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
        }
    }

    private fun onSuccess(@IdRes id: Int) {
        println("onSuccess")
        GlobalScope.launch(Dispatchers.Main) {
            println("dispatched")
            val button = findViewById<Button>(id)
            button.setBackgroundColor(
                ContextCompat.getColor(this@SSCertificateActivity, R.color.success)
            )
        }
    }

    private fun onError(@IdRes id: Int, message: String) {
        GlobalScope.launch(Dispatchers.Main) {
            val button = findViewById<Button>(id)
            button.setBackgroundColor(
                ContextCompat.getColor(this@SSCertificateActivity, R.color.failure)
            )

            val duration = Toast.LENGTH_LONG
            val toast = Toast.makeText(this@SSCertificateActivity, message, duration)
            toast.show()
        }
    }


/*
    fun sendConfigPinned(view: View) {
        GlobalScope.launch(Dispatchers.IO) {
            onStart(R.id.config_pinned)
            try {
                // Pinned by hash in network config:
                val mURL = URL("https://www.youtube.com")
                with(mURL.openConnection() as HttpsURLConnection) {
                    println("URL: ${this.url}")
                    println("Response Code: ${this.responseCode}")
                }

                onSuccess(R.id.config_pinned)
            } catch (e: Throwable) {
                println(e)
                onError(R.id.config_pinned, e.toString())
            }
        }
    }*/

    fun sendOkHttpPinned() {
        GlobalScope.launch(Dispatchers.IO) {
            onStart(R.id.okhttp_pinned)

            try {
                val hostname = "youtube.com"

                val client = SSLCertificate.sendOkHttpPinned(hostname, DIGICERT_ROOT_SHA256)
                val request = Request.Builder()
                    .url("https://www.youtube.com")
                    .build()

                if (client != null) {
                    client.newCall(request).execute().use { response ->
                        println("URL: ${request.url}")
                        println("Response Code: ${response.code}")
                    }
                }

                onSuccess(R.id.okhttp_pinned)
            } catch (e: Throwable) {
                println(e)
                onError(R.id.okhttp_pinned, e.toString())
            }
        }
    }

    fun sendVolleyPinned() {
        onStart(R.id.volley_pinned)

        try {
            val context =
                SSLCertificate.sendVolleyPinned(R.raw.digicert_ca, this@SSCertificateActivity)
            val requestQueue = RequestQueue(
                NoCache(),
                BasicNetwork(HurlStack(null, context))
            )
            requestQueue.start()

            // Make a request using that client:
            val stringRequest = StringRequest(
                com.android.volley.Request.Method.GET,
                "https://www.sha512.badssl.com",
                { _ ->
                    println("Volley success")
                    this@SSCertificateActivity.onSuccess(R.id.volley_pinned)
                },
                {
                    println(it.toString())
                    this@SSCertificateActivity.onError(R.id.volley_pinned, it.toString())
                }
            )

            requestQueue.add(stringRequest)
        } catch (e: Throwable) {
            println(e)
            onError(R.id.volley_pinned, e.toString())
        }
    }

    fun sendManuallyCustomPinned() {
        GlobalScope.launch(Dispatchers.IO) {
            onStart(R.id.manually_pinned)
            try {
                if (SSLCertificate.sendManuallyCustomPinned(
                        DIGICERT_ROOT_SHA256,
                        "sha512.badssl.com",
                        443
                    )
                ) {
                    onSuccess(R.id.manually_pinned)
                } else {
                    onError(R.id.manually_pinned, "unrecongnized cert hash")
                }
            } catch (e: Throwable) {
                println(e)
                onError(R.id.manually_pinned, e.toString())
            }
        }
    }

}