package com.aihangxunxi.aitalk.storage.model.attachment;

/**
 * @author chenqingze107@163.com
 * @version 1.0
 *
 */
public class AudioAttachment extends FileAttachment {

	private long duration;

	private static final String KEY_DURATION = "dur";

	public AudioAttachment() {
	}

	public AudioAttachment(String attach) {
		super(attach);
	}

	public long getDuration() {
		return this.duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	// protected b storageType() {
	// return b.c;
	// }
	//
	// protected void save(JSONObject paramJSONObject) {
	// g.a(paramJSONObject, "dur", this.duration);
	// }
	//
	// protected void load(JSONObject paramJSONObject) {
	// this.duration = g.a(paramJSONObject, "dur");
	// }

}
