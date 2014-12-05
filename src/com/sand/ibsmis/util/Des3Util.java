package com.sand.ibsmis.util;

import java.io.PrintStream;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Des3Util
{
  private SecretKey key;

  public Des3Util(String sharekey)
  {
    try
    {
      byte[] keyBytes = { 124, -75, -14, 52, 109, 47, 109, 117, 14, 122, -56, 37, 4, -89, 41, 47, -36, -98, 82, 91, -125, 70, 109, 14 };
      this.key = new SecretKeySpec(keyBytes, "DESede");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void getKey(String strKey)
  {
    try
    {
      KeyGenerator _generator = KeyGenerator.getInstance("DESede");
      _generator.init(168, new SecureRandom(strKey.getBytes()));
      this.key = _generator.generateKey();
      _generator = null;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public String getEncString(String strMing)
  {
    byte[] byteMi = (byte[])null;
    byte[] byteMing = (byte[])null;
    String strMi = "";
    BASE64Encoder base64en = new BASE64Encoder();
    try {
      byteMing = strMing.getBytes("UTF8");
      byteMi = getEncCode(byteMing);
      strMi = base64en.encode(byteMi);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base64en = null;
      byteMing = (byte[])null;
      byteMi = (byte[])null;
    }
    return strMi;
  }

  public String getDesString(String strMi)
  {
    BASE64Decoder base64De = new BASE64Decoder();
    byte[] byteMing = (byte[])null;
    byte[] byteMi = (byte[])null;
    String strMing = "";
    try {
      byteMi = base64De.decodeBuffer(strMi);
      byteMing = getDesCode(byteMi);
      strMing = new String(byteMing, "UTF8");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base64De = null;
      byteMing = (byte[])null;
      byteMi = (byte[])null;
    }
    return strMing;
  }

  private byte[] getEncCode(byte[] byteS)
  {
    byte[] byteFina = (byte[])null;
    Cipher cipher;
    try
    {
      cipher = Cipher.getInstance("DESede");
      cipher.init(1, this.key);
      byteFina = cipher.doFinal(byteS);
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally
    {
      cipher = null;
    }
    return byteFina;
  }

  private byte[] getDesCode(byte[] byteD)
  {
    byte[] byteFina = (byte[])null;
    Cipher cipher;
    try
    {
      cipher = Cipher.getInstance("DESede");
      cipher.init(2, this.key);
      byteFina = cipher.doFinal(byteD);
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally
    {
      cipher = null;
    }
    return byteFina;
  }

  public static String byte2hex(byte[] b)
  {
    String hs = "";
    String stmp = "";
    for (int n = 0; n < b.length; n++) {
      stmp = Integer.toHexString(b[n] & 0xFF);
      if (stmp.length() == 1)
        hs = hs + "0" + stmp;
      else
        hs = hs + stmp;
    }
    return hs.toUpperCase();
  }

  public static byte[] hex2byte(byte[] b)
  {
    if (b.length % 2 != 0)
      throw new IllegalArgumentException("长度不是偶数");
    byte[] b2 = new byte[b.length / 2];
    for (int n = 0; n < b.length; n += 2) {
      String item = new String(b, n, 2);
      b2[(n / 2)] = (byte)Integer.parseInt(item, 16);
    }
    return b2;
  }

  public static void main(String[] args) {
    Des3Util des = new Des3Util("sand1234");

    String strEnc = des
      .getEncString("sandsand");
    System.out.println(strEnc);
    String strDes = des.getDesString("7Fu5IEgs5NJ+bm9XrqTl5w==");
    System.out.println(strDes);
  }
}

