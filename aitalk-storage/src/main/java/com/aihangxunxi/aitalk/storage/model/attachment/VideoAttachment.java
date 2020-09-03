package com.aihangxunxi.aitalk.storage.model.attachment;

/**
 * @author chenqingze107@163.com
 * @version 1.0

 */
public class VideoAttachment extends FileAttachment {

	private int width;

	private int height;

	private long duration;

	private static final String KEY_DURATION = "dur";

	private static final String KEY_WIDTH = "w";

	private static final String KEY_HEIGHT = "h";

	public VideoAttachment() {
	}

	public VideoAttachment(String attach) {
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

	public long getDuration() {
		return this.duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	// public String getThumbUrl() {
	// return i.a(this, getUrl());
	// }
	//
	// protected b storageType() {
	// return b.e;
	// }

	// protected void save(JSONObject paramJSONObject) {
	// g.a(paramJSONObject, "w", this.width);
	// g.a(paramJSONObject, "h", this.height);
	// g.a(paramJSONObject, "dur", this.duration);
	// }
	//
	// protected void load(JSONObject paramJSONObject) {
	// this.width = g.a(paramJSONObject, "w");
	// this.height = g.a(paramJSONObject, "h");
	// this.duration = g.a(paramJSONObject, "dur");
	// }

}
