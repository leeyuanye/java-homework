package org.geekbang.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;

public class MyClassLoader extends ClassLoader {

  public static void main(String[] args) throws Exception {
    MyClassLoader classLoader = new MyClassLoader();
    Class clazz = classLoader.findClass("Hello");
    Method m = clazz.getMethod("hello");
    m.invoke(clazz.newInstance());
  }

  @Override
  protected Class<?> findClass(String name) throws ClassNotFoundException {
    ClassLoader classLoader = getClass().getClassLoader();
    URL url = classLoader.getResource("Hello.xlass");
    File file = new File(url.getFile());
    InputStream fis = null;
    ByteArrayOutputStream bos = null;
    byte[] bytes = new byte[1024];
    byte[] readBytes = null;
    try {
      bos = new ByteArrayOutputStream();
      fis = new FileInputStream(file);
      int length;
      while ((length = fis.read(bytes))!= -1){
        bos.write(bytes,0,length);
      }
      readBytes = bos.toByteArray();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if(fis != null){
        try {
          fis.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (bos != null){
        try {
          bos.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    for (int i=0;i<readBytes.length;i++){
      int a = readBytes[i]+128;
      byte x = (byte) (127-a);
      readBytes[i] = x;
    }
    return defineClass(name,readBytes,0,readBytes.length);
  }
}
