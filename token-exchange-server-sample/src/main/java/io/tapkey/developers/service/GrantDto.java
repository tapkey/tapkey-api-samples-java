package io.tapkey.developers.service;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GrantDto {
    private String id;
    private String boundLockId;
    private String contactId;
    private String status;

    public GrantDto() {
    }

    public GrantDto(String contactId, String boundLockId) {
        this.setBoundLockId(boundLockId);
        this.setContactId(contactId);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBoundLockId() {
        return boundLockId;
    }

    public void setBoundLockId(String boundLockId) {
        this.boundLockId = boundLockId;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
