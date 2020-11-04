package com.aihangxunxi.aitalk.im.utils;

import java.io.*;

public class ObjectUtil {

	// 对象转化为字节码
	public static byte[] getBytesFromObject(Serializable obj) {
		if (obj == null) {
			return null;
		}
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oo = new ObjectOutputStream(bo);
			oo.writeObject(obj);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return bo.toByteArray();
	}

	// 字节码转化为对象
	public static Object getObjectFromBytes(byte[] objBytes) {
		if (objBytes == null || objBytes.length == 0) {
			return null;
		}
		Object obj = null;
		ByteArrayInputStream bi = new ByteArrayInputStream(objBytes);
		try {
			ObjectInputStream oi = new ObjectInputStream(bi);
			obj = oi.readObject();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

}
