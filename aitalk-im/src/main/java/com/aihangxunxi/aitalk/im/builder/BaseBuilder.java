package com.aihangxunxi.aitalk.im.builder;

import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.storage.model.BaseModel;

/**
 * @author chenqingze107@163.com
 * @version 1.0
 */
public interface BaseBuilder {

	Message toProtoBuffer(BaseModel baseModel);

	BaseModel toModel(Message message);

}
