package com.aihangxunxi.aitalk.storage.domain.msg.attachment;

/**
 * @author chenqingze107@163.com
 * @version 1.0
 * @since 2020/3/19
 */
public class ImageAttachment extends FileAttachment {

	private int width;

	private int height;

	private static final String KEY_WIDTH = "w";

	private static final String KEY_HEIGHT = "h";

	public ImageAttachment() {
	}

	public ImageAttachment(String attach) {
		super(attach);
	}

	public int getWidth() {
		return this.width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return this.height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	// public String getThumbUrl() {
	// return i.a(this, getUrl());
	// }

	public boolean isHdImage() {
		return false;
	}

	// protected b storageType() {
	// return b.d;
	// }

	// protected void save(JSONObject json) {
	// g.a(json, "w", this.width);
	// g.a(json, "h", this.height);
	// }
	//
	// protected void load(json json) {
	// this.width = g.a(json, "w");
	// this.height = g.a(json, "h");
	// }

}
