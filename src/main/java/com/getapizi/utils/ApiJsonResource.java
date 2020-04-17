package com.getapizi.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;

public class ApiJsonResource {
	public static <T> T instantiate(String url, Class<T> klazz) throws MalformedURLException, IOException {
		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		return instantiate(connection.getInputStream(), klazz);
	}

	public static <T> T instantiate(InputStream inputStream, Class<T> klazz) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line+"\n");
        }
        br.close();

        T object = new Gson().fromJson(sb.toString(), klazz);
        return object;
	}
}
