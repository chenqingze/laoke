package com.aihangxunxi.aitalk.storage.model;

public class GroupSetting {

	private boolean isMute;

	private boolean isConfirmJoin;

	private boolean isTop;

	public boolean getIsMute() {
		return isMute;
	}

	public void setIsMute(boolean mute) {
		isMute = mute;
	}

	public boolean getIsConfirmJoin() {
		return isConfirmJoin;
	}

	public void setIsConfirmJoin(boolean confirmJoin) {
		isConfirmJoin = confirmJoin;
	}

	public boolean getIsTop() {
		return isTop;
	}

	public void setIsTop(boolean top) {
		isTop = top;
	}

}
