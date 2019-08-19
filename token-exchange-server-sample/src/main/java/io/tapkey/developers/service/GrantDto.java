package io.tapkey.developers.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GrantDto {
    private String id;
    private String boundLockId;
    private String contactId;
    private String state;
    private Date validBefore;
    private Date validFrom;
    private String timeRestrictionIcal;
    private String physicalLockId;

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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getValidBefore() {
        return validBefore;
    }

    public void setValidBefore(Date validBefore) {
        this.validBefore = validBefore;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public String getTimeRestrictionIcal() {
        return timeRestrictionIcal;
    }

    public void setTimeRestrictionIcal(String timeRestrictionIcal) {
        this.timeRestrictionIcal = timeRestrictionIcal;
    }

    public String getPhysicalLockId() {
        return physicalLockId;
    }

    public void setPhysicalLockId(String physicalLockId) {
        this.physicalLockId = physicalLockId;
    }

    @JsonProperty("boundLock")
    private void unpackNested(Map<String, Object> boundLock) {
        setPhysicalLockId((String) boundLock.get("physicalLockId"));
    }
}
