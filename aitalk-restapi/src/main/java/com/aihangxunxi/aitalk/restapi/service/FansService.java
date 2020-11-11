package com.aihangxunxi.aitalk.restapi.service;

import com.aihangxunxi.aitalk.storage.model.Fans;

import java.util.List;

public interface FansService {

	List<Fans> queryFans(Long userId);

	boolean followStore(Long storeId, Long userId);

	boolean cancelFollow(Long storeId, Long userId);

	boolean followed(Long storeId, Long userId);

}
