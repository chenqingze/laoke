package com.aihangxunxi.aitalk.storage.domain.msg.attachment;

/**
 * @author chenqingze107@163.com
 * @version 1.0

 */
public class LocationAttachment implements MsgAttachment {

	private double latitude;

	private double longitude;

	private String address;

	private static final String KEY_LATITUDE = "lat";

	private static final String KEY_LONGITUDE = "lng";

	private static final String KEY_DESC = "title";

	public LocationAttachment() {
	}

	public LocationAttachment(String attach) {
		fromJson(attach);
	}

	public double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	private void fromJson(String json) {
		// JSONObject jSONObject = g.a(json);
		// this.latitude = g.d(jSONObject, "lat");
		// this.longitude = g.d(jSONObject, "lng");
		// this.address = g.e(jSONObject, "title");
	}

	/**
	 * 从接口复制的说明: MsgAttachment 将消息附件序列化为字符串，存储到消息数据库或发送到服务器。
	 * @param send 如果你的附件在本地需要存储额外数据，而这些数据不需要发送到服务器，可依据该参数值区别对待。
	 * @return
	 */
	public String toJson(boolean send) {
		// JSONObject jSONObject = new JSONObject();
		// try {
		// jSONObject.put("lat", this.latitude);
		// jSONObject.put("lng", this.longitude);
		// jSONObject.put("title", this.address);
		// } catch (Exception exception) {
		// null.printStackTrace();
		// }
		// return jSONObject.toString();
		return null;
	}

}
