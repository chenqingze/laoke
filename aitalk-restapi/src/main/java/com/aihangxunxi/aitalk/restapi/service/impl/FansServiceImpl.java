package com.aihangxunxi.aitalk.restapi.service.impl;

import com.aihangxunxi.aitalk.restapi.service.FansService;
import com.aihangxunxi.aitalk.storage.model.Fans;
import com.aihangxunxi.aitalk.storage.repository.FansRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FansServiceImpl implements FansService {

	@Resource
	private FansRepository fansRepository;

	@Override
	public List<Fans> queryFans(Long userId) {
		return fansRepository.queryFans(userId);
	}

	@Override
	public boolean followStore(Long storeId, Long userId) {
		return fansRepository.follow(storeId, userId);
	}

	@Override
	public boolean cancelFollow(Long storeId, Long userId) {
		return fansRepository.cancelFollow(storeId, userId);
	}

	@Override
	public boolean followed(Long storeId, Long userId) {
		return fansRepository.followed(storeId, userId);
	}

}
