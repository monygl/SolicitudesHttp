package com.example.solicitudeshttp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.telecom.Call
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import okhttp3.OkHttpClient
import java.io.InputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.android.synthetic.main.activity_main.*
import okio.IOException


class MainActivity : AppCompatActivity(), CompletadoListener {

    override fun DescargaCompleta(resultado: String) {
       Log.d("DescarcaCompleta", resultado)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        bValidarRed.setOnClickListener {

            if (Network.hayRed(this)){
                Toast.makeText(this,"SI hay Red", Toast.LENGTH_LONG).show()
            }else
            {
                Toast.makeText(this,"NO hay una conexión a Internet", Toast.LENGTH_LONG).show()
            }
        }
        bSolicitudHttp.setOnClickListener {
            if (Network.hayRed(this)) {
               /* Log.d("bSolicitudonClick",DescargarDatos("http://www.google.com"))
                DescargarDatos("http://www.google.com")*/
                DescargaURL(this).execute("http://www.google.com")
            } else {
                Toast.makeText(
                    this,
                    "Asegurate que haya una conexión a Internet",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        bVolley.setOnClickListener{

            if (Network.hayRed(this)) {
                 SolicitudHttpVolley("http://www.google.com")
            } else {
                Toast.makeText(
                    this,
                    "Asegurate que haya una conexión a Internet",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        bOK.setOnClickListener{
            if (Network.hayRed(this)) {
               SolicitudHttpOKHttp("http://www.google.com")
                  } else {

                              Toast.makeText(
                    this,
                    "Asegurate que haya una conexión a Internet",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }


    //metodo para volley
    private fun SolicitudHttpVolley (url:String) {
       val queue = Volley.newRequestQueue(this)
       val solicitud = StringRequest(Request.Method.GET, url, Response.Listener <String>{
          response ->
           try{
               Log.d("SolicitudHttpVolley", response)
           }catch (e:Exception){

           }

       }, Response.ErrorListener {  } )
        queue.add(solicitud)
    }
    //metodo para OK
    private fun SolicitudHttpOKHttp(url:String){
        val cliente = OkHttpClient()
        val solicitud = okhttp3.Request.Builder().url(url).build()
        cliente.newCall(solicitud).enqueue(object: okhttp3.Callback{

            override fun onFailure(call: okhttp3.Call, e: IOException) {

            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
               val resultado = response.body?.string()!!
                this@MainActivity.runOnUiThread{
                    try{
                      Log.d("solicitudHTTP",resultado)
                    }catch(e:Exception){

                    }

                }

            }


        })

    }
}
