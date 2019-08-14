package io.tapkey.developers.service;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContactDto {
    private String id;
    private String ipId;
    private String ipUserId;

    public ContactDto() {
    }

    public ContactDto(String ipId, String ipUserId) {
        this.setIpUserId(ipUserId);
        this.setIpId(ipId);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIpId() {
        return ipId;
    }

    public void setIpId(String ipId) {
        this.ipId = ipId;
    }

    public String getIpUserId() {
        return ipUserId;
    }

    public void setIpUserId(String ipUserId) {
        this.ipUserId = ipUserId;
    }

}
