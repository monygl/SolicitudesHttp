package com.example.solicitudeshttp

import android.os.AsyncTask
import android.os.StrictMode
import java.io.IOException
import java.io.InputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class DescargaURL(var CompletadoListener:CompletadoListener?):AsyncTask<String, Void, String>() {
    override fun doInBackground(vararg params: String): String? {

        try{
            return DescargarDatos(params[0])
        }catch(e:IOException){
            return null
        }
    }

    override fun onPostExecute(result: String) {
        try{
            CompletadoListener?.DescargaCompleta(result)
        }catch (e: Exception){

        }
    }
    @Throws(IOException::class)
    private fun DescargarDatos(url:String):String{

      /*  val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)*/

        var InputStream: InputStream? = null
        try{
            var url = URL(url)
            var conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "GET"
            conn.connect()
            InputStream = conn.inputStream

            return InputStream.bufferedReader().use {
                it.readText()
            }
        }finally{
            if (InputStream != null) {
                InputStream.close()
            }
        }
    }

}