package com.aihangxunxi.aitalk.storage.domain.team.model;

/**
 * @author chenqingze107@163.com
 * @version 1.0
 */
public interface ITeam {

	/**
	 * 获取群组公告
	 * @return
	 */
	String getAnnouncement();

	/**
	 * 获取群组的创建时间
	 * @return
	 */
	long getCreateTime();

	/**
	 * 获取创建群组的用户帐号
	 * @return
	 */
	String getCreator();

	/**
	 * 获取群组扩展配置。
	 * @return
	 */
	String getExtension();

	String getExtServer();

	String getIcon();

	String getId();

	String getIntroduce();

	int getMemberCount();

	int getMemberLimit();

	// TeamMessageNotifyTypeEnum getMessageNotifyType();

	// TeamAllMuteModeEnum getMuteMode();

	String getName();

	// TeamBeInviteModeEnum getTeamBeInviteMode();

	// TeamExtensionUpdateModeEnum getTeamExtensionUpdateMode();

	// TeamInviteModeEnum getTeamInviteMode();

	// TeamUpdateModeEnum getTeamUpdateMode();

	// TeamTypeEnum getType();

	// VerifyTypeEnum getVerifyType();

	boolean isAllMute();

	boolean isMyTeam();

	boolean mute();

	void setExtension(String extension);

}
