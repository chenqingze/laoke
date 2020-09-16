package com.aihangxunxi.aitalk.im.session;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author chenqingze107@163.com
 * @version 1.0
 * @deprecated {@link Session} 实现类，增加了{@link Map}支持
 */
public final class MapSession implements Session {

    private String id;

    private Map<String, Object> sessionAttrs = new HashMap<>();

    private Instant creationTime = Instant.now();

    private Instant lastCommunicatedTime = this.creationTime;

    public MapSession(ChannelHandlerContext context) {
        Channel channel = context.channel();
        this.id = channel.id().asLongText();
        this.setAttribute(SessionConstant.CHANNEL_KEY, channel);
    }

    public MapSession(Session session) {
        if (session == null) {
            throw new IllegalArgumentException("session cannot be null");
        }
        this.id = session.getId();
        this.sessionAttrs = new HashMap<>(session.getAttributeNames().size());
        for (String attrName : session.getAttributeNames()) {
            Object attrValue = session.getAttribute(attrName);
            if (attrValue != null) {
                this.sessionAttrs.put(attrName, attrValue);
            }
        }
        this.creationTime = session.getCreationTime();
        this.lastCommunicatedTime = session.getLastCommunicatedTime();
    }

    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Instant getCreationTime() {
        return this.creationTime;
    }

    public void setCreationTime(Instant creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public void setLastCommunicatedTime(Instant lastCommunicatedTime) {
        this.lastCommunicatedTime = lastCommunicatedTime;
    }

    @Override
    public Instant getLastCommunicatedTime() {
        return this.lastCommunicatedTime;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getAttribute(String attributeName) {
        return (T) this.sessionAttrs.get(attributeName);
    }

    @Override
    public Set<String> getAttributeNames() {
        return new HashSet<>(this.sessionAttrs.keySet());
    }

    @Override
    public void setAttribute(String attributeName, Object attributeValue) {
        if (attributeValue == null) {
            removeAttribute(attributeName);
        } else {
            this.sessionAttrs.put(attributeName, attributeValue);
        }
    }

    @Override
    public void removeAttribute(String attributeName) {
        this.sessionAttrs.remove(attributeName);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Session && this.id.equals(((Session) obj).getId());
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    // private static String generateId() {
    // return UUID.randomUUID().toString();
    // }

}
