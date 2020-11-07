package com.aihangxunxi.aitalk.restapi.service.impl;

import com.aihangxunxi.aitalk.restapi.service.MsgHisService;
import com.aihangxunxi.aitalk.storage.model.MsgHist;
import com.aihangxunxi.aitalk.storage.model.OfflineMsg;
import com.aihangxunxi.aitalk.storage.repository.MsgHistRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author guoyongsheng Data: 2020/11/6
 * @Version 3.0
 */
@Service
public class MsgHisServiceImpl implements MsgHisService {

	@Resource
	private MsgHistRepository msgHistRepository;

	/**
	 * 更具receiverId获取离线消息, 将此消息 再offlineMsg 中删除 并将此消息列表插入至MsgHis中
	 * @param id
	 * @return
	 */
	@Override
	public List<OfflineMsg> getOfflineMsg(String id) {
		// 获取符合条件的 未读消息 offlineMsg
		List<OfflineMsg> offlineMsg = msgHistRepository.getOfflineMsg(new ObjectId(id));
		if (offlineMsg != null && !offlineMsg.isEmpty()) {
			// 将类型转换成Int
			for(OfflineMsg msg : offlineMsg) {
				msg.setMsgStatusInt(msg.getMsgStatus().ordinal());
				msg.setConversationTypeInt(msg.getConversationType().ordinal());
				msg.setMsgTypeInt(msg.getMsgType().ordinal());
			}
			msgHistRepository.deleteOfflineMsg(new ObjectId(id));
		}
		return offlineMsg;
	}

	/**
	 * 获取最后一条的消息列表 根据咨询方向查询和receiverId接受者
	 * @param id
	 * @return
	 */
	@Override
	public List<MsgHist> getOfflineLastMsg(String id) {
		return null;
	}

}
