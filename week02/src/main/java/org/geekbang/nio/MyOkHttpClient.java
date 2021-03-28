package org.geekbang.nio;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyOkHttpClient {

  private OkHttpClient client = new OkHttpClient();

  public static void main(String[] args) throws IOException {

    MyOkHttpClient client = new MyOkHttpClient();
    System.out.println(client.run("http://localhost:8801"));

  }

  String run(String url) throws IOException {
    Request request = new Request.Builder()
        .url(url)
        .build();

    try (Response response = client.newCall(request).execute()) {
      return response.body().string();
    }
  }

}
