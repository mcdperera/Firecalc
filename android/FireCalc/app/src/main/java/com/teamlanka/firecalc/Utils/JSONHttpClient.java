package com.teamlanka.firecalc.Utils;

import android.util.Log;

import com.google.gson.GsonBuilder;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.*;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 * Created by Damith on 1/31/2016.
 * remarks http://hmkcode.com/android-send-json-data-to-server/
 */
public class JSONHttpClient {
    private <T> T PostObject(final String url, final JSONObject jsonObject, final Class<T> objectClass) {

        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();

        HttpPost httpPost = new HttpPost(url);

        try {

            Log.e("url", url);

            StringEntity stringEntity = new StringEntity(jsonObject.toString());

            httpPost.setEntity(stringEntity);

            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            HttpResponse httpResponse = defaultHttpClient.execute(httpPost);

            InputStream inputStream = httpResponse.getEntity().getContent();

            String resultString = convertStreamToString(inputStream);

            Log.e("resultString", resultString);

            if (resultString.isEmpty() || resultString == null) {
                return null;
            } else {
                return new GsonBuilder().create().fromJson(resultString, objectClass);
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClientProtocolException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    public <T> T Post(String url, final List<NameValuePair> params, final Class<T> objectClass) {
        String paramString = URLEncodedUtils.format(params, "utf-8");
        url += "?" + paramString;


        Log.e("Postxxxxxxx", url);

        return PostObject(url, null, objectClass);
    }

    public <T> T Post(String url, final JSONObject jsonObject, final Class<T> objectClass) {
        //String paramString = URLEncodedUtils.format(params, "utf-8");
        //url += "?" + paramString;
        return PostObject(url, jsonObject, objectClass);
    }

    private String convertStreamToString(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        return stringBuilder.toString();
    }

    public <T> T Get(String url, List<NameValuePair> params, final Class<T> objectClass) {
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        String paramString = URLEncodedUtils.format(params, "utf-8");
        url += "?" + paramString;
        Log.e("GetMethod", url);
        HttpGet httpGet = new HttpGet(url);
        try {

            httpGet.setHeader("Accept", "application/json");
            httpGet.setHeader("Accept-Encoding", "gzip");

            HttpResponse httpResponse = defaultHttpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                InputStream inputStream = httpEntity.getContent();
                Header contentEncoding = httpResponse.getFirstHeader("Content-Encoding");

                if (contentEncoding != null && contentEncoding.getValue().equalsIgnoreCase("gzip")) {
                    inputStream = new GZIPInputStream(inputStream);
                }

                String resultString = convertStreamToString(inputStream);
                inputStream.close();

                Log.v("Tag", resultString);

                return new GsonBuilder().create().fromJson(resultString, objectClass);

            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClientProtocolException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    public boolean Delete(String url, final List<NameValuePair> params) {
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        String paramString = URLEncodedUtils.format(params, "utf-8");
        url += "?" + paramString;
        HttpDelete httpDelete = new HttpDelete(url);

        HttpResponse httpResponse = null;
        try {
            httpResponse = defaultHttpClient.execute(httpDelete);
            return httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_NO_CONTENT;
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return false;
    }
}

