package com.aihangxunxi.aitalk.storage.model;

public class GroupSetting {

	private boolean isMute;

	private boolean isConfirmJoin;

	public boolean isMute() {
		return isMute;
	}

	public void setIsMute(boolean mute) {
		isMute = mute;
	}

	public boolean isConfirmJoin() {
		return isConfirmJoin;
	}

	public void setIsConfirmJoin(boolean confirmJoin) {
		isConfirmJoin = confirmJoin;
	}
}
