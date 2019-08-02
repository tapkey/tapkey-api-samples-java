package io.tapkey.developers.service;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class IpUserDto {
    private String id;
    private String ipId;
    private String ipUserId;
    private String email;
    private String status;

    public IpUserDto() {
    }

    public IpUserDto(String ipId, String ipUserId) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
