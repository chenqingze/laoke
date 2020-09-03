package com.aihangxunxi.aitalk.storage.model.attachment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Strings;

import java.io.File;

/**
 * @author chenqingze107@163.com
 * @version 1.0
 */
public class FileAttachment implements MsgAttachment {

    protected String path;// 文件路径

    protected long size;// 文件大小

    protected String md5;// 文件内容的MD5

    protected String url;// 文件下载地址

    protected String displayName;// 文件显示名

    protected String extension; // 文件后缀名、扩展名

    protected boolean forceUpload = false;// 如果服务器存在相同的附件文件，是否强制重新上传 ， 默认false

    protected String nosTokenSceneKey = "im_default_im";// 上传文件时用的对token对应的场景，默认imNosSceneKeyConstant#NIM_DEFAULT_IM

    private static final String KEY_PATH = "path";

    private static final String KEY_NAME = "name";

    private static final String KEY_SIZE = "size";

    private static final String KEY_MD5 = "md5";

    private static final String KEY_URL = "url";

    private static final String KEY_EXT = "ext";

    private static final String KEY_SCENE = "sen";

    private static final String KEY_FORCE_UPLOAD = "force_upload";

    public FileAttachment() {
    }

    public FileAttachment(String attach) {
        fromJson(attach);
    }

    public String getPath() {
        String str = getPathForSave();
        return (new File(str)).exists() ? str : null;
    }

    public String getPathForSave() {
        // if (!Strings.isNullOrEmpty(this.path))
        return this.path;
    }

    public String getThumbPath() {
        String str = getThumbPathForSave();
        return (new File(str)).exists() ? str : null;
    }

    // todo
    public String getThumbPathForSave() {
        return null;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getNosTokenSceneKey() {
        return nosTokenSceneKey;
    }

    public void setNosTokenSceneKey(String nosTokenSceneKey) {
        this.nosTokenSceneKey = nosTokenSceneKey;
    }

    public boolean isForceUpload() {
        return forceUpload;
    }

    public void setForceUpload(boolean forceUpload) {
        this.forceUpload = forceUpload;
    }

    public String getFileName() {

        if (!(this.path == null || this.path.isEmpty())) {
            String str = this.path;
            int i = this.path.lastIndexOf('/');
            if (i != -1)
                return str.substring(i + 1);
            return str;
        }
        // todo:通过url解析文件名;
        // if (!Strings.isNullOrEmpty
        // (this.md5))
        // return
        return this.md5;
    }

    protected void save(ObjectNode json) {
    }

    protected void load(JsonNode json) {
    }

    @Override
    public String toJson(boolean send) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();

        try {
            if (!send && !Strings.isNullOrEmpty(this.path))
                objectNode.put(KEY_PATH, this.path);
            if (!Strings.isNullOrEmpty(this.md5))
                objectNode.put(KEY_MD5, this.md5);
            if (!Strings.isNullOrEmpty(this.displayName))
                objectNode.put(KEY_NAME, this.displayName);
            objectNode.put(KEY_URL, this.url);
            objectNode.put(KEY_SIZE, this.size);
            if (!Strings.isNullOrEmpty(this.extension))
                objectNode.put(KEY_EXT, this.extension);
            if (!Strings.isNullOrEmpty(this.nosTokenSceneKey))
                objectNode.put(KEY_SCENE, this.nosTokenSceneKey);
            objectNode.put(KEY_FORCE_UPLOAD, this.forceUpload);
            save(objectNode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objectNode.toString();
    }

    private void fromJson(String json) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode jsonNode = objectMapper.readTree(json);//json->JsoNode
            this.path = jsonNode.get(KEY_PATH).asText();
            this.md5 = jsonNode.get(KEY_MD5).asText();
            this.url = jsonNode.get(KEY_URL).asText();
            this.displayName = jsonNode.get(KEY_NAME).asText();
            this.size = jsonNode.get(KEY_SIZE).asLong();
            this.extension = jsonNode.get(KEY_EXT).asText();
            setNosTokenSceneKey(jsonNode.get(KEY_SCENE).asText());
            this.forceUpload = jsonNode.get(KEY_FORCE_UPLOAD).asBoolean();
            load(jsonNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

}
