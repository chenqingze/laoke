package com.aihangxunxi.aitalk.restapi.service;

import com.aihangxunxi.aitalk.storage.model.MsgHist;
import com.aihangxunxi.aitalk.storage.model.OfflineMsg;

import java.util.List;

public interface MsgHisService {

	List<OfflineMsg> getOfflineMsg(String id);

	List<MsgHist> getOfflineLastMsg(String id);

}
