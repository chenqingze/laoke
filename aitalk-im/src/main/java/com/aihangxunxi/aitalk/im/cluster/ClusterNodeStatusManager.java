package com.aihangxunxi.aitalk.im.cluster;

import com.github.benmanes.caffeine.cache.Cache;
import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.Lease;
import io.etcd.jetcd.lease.LeaseKeepAliveResponse;
import io.etcd.jetcd.options.PutOption;
import io.etcd.jetcd.support.CloseableClient;
import io.etcd.jetcd.support.Observers;
import io.grpc.stub.StreamObserver;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 注册节点到etcd集群,节点可用状态更新,集群节点状态管理
 *
 * @author chenqingze107@163.com
 * @version 2.0 2020/10/10 3:19 PM
 */
public class ClusterNodeStatusManager {

	private static final Logger logger = LoggerFactory.getLogger(ClusterNodeStatusManager.class);

	private final Client client;

	private final KV kvClient;

	private final Lease leaseClient;

	private final Cache<String, Channel> localChannelCache;

	private final ByteSequence key;

	private final long leaseID;

	private final long ttl = 5;

	public ClusterNodeStatusManager(Cache<String, Channel> localChannelCache, String... endpoints)
			throws ExecutionException, InterruptedException, UnknownHostException {
		this.client = Client.builder().endpoints(endpoints).build();
		this.kvClient = client.getKVClient();
		this.leaseClient = client.getLeaseClient();
		this.localChannelCache = localChannelCache;
		this.leaseID = leaseClient.grant(ttl).get().getID();
		InetAddress inetAddress = InetAddress.getLocalHost();
		String keyStr = String.join("-", inetAddress.getHostName(), inetAddress.getHostAddress(),
				String.valueOf(leaseID));
		this.key = ByteSequence.from(keyStr, StandardCharsets.UTF_8);

	}

	/**
	 * todo：细化完善
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public void registerNode() throws ExecutionException, InterruptedException {
		ByteSequence value = ByteSequence.from(String.valueOf(localChannelCache.estimatedSize()),
				StandardCharsets.UTF_8);
		kvClient.put(key, value, PutOption.newBuilder().withLeaseId(leaseID).build()).get();
		if (logger.isDebugEnabled()) {
			logger.debug("key reversion count:{}", kvClient.get(key).get().getCount());
		}
		AtomicReference<LeaseKeepAliveResponse> responseRef = new AtomicReference<>();
		StreamObserver<LeaseKeepAliveResponse> observer = Observers.observer(response -> {
			responseRef.set(response);
		});

		CloseableClient c = leaseClient.keepAlive(leaseID, observer);
		LeaseKeepAliveResponse response = responseRef.get();

	}

	public void UnregisterNode() throws ExecutionException, InterruptedException {
		leaseClient.revoke(leaseID).get();
		if (logger.isDebugEnabled()) {
			logger.debug("key reversion count:{}", kvClient.get(key).get().getCount());
		}
		if (client != null) {
			client.close();
		}
	}

	// todo:获取节点状态，是否存活
	public boolean isNodeAlive() {
		return true;
	}

}
