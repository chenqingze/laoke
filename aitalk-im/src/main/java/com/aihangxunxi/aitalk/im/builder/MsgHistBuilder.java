package com.aihangxunxi.aitalk.im.builder;

import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.storage.model.BaseModel;
import org.springframework.stereotype.Component;

/**
 * @author chenqingze107@163.com
 * @version 1.0
 */
@Component
public class MsgHistBuilder implements BaseBuilder {

	@Override
	public Message toProtoBuffer(BaseModel baseModel) {
		return null;
	}

	@Override
	public BaseModel toModel(Message message) {
		return null;
	}

}
