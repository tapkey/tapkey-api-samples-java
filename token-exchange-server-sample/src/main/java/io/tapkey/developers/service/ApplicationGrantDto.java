package io.tapkey.developers.service;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationGrantDto {
    private String id;
    private String state;
    private Date validBefore;
    private Date validFrom;
    private String timeRestrictionIcal;
    private String issuer;
    private String granteeFirstName;
    private String granteeLastName;
    private String lockTitle;
    private String lockLocation;
    private String physicalLockId;

    public ApplicationGrantDto() {
    }

    public ApplicationGrantDto(String granteeFirstName, String granteeLastName, String issuer, String lockTitle, String lockLocation, GrantDto tapkeyGrant) {
        this.setGranteeFirstName(granteeFirstName);
        this.setGranteeLastName(granteeLastName);
        this.setIssuer(issuer);
        this.setLockTitle(lockTitle);
        this.setLockLocation(lockLocation);
        this.setId(tapkeyGrant.getId());
        this.setState(tapkeyGrant.getState());
        this.setValidBefore(tapkeyGrant.getValidBefore());
        this.setValidFrom(tapkeyGrant.getValidFrom());
        this.setTimeRestrictionIcal(tapkeyGrant.getTimeRestrictionIcal());
        this.setPhysicalLockId(tapkeyGrant.getPhysicalLockId());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getGranteeFirstName() {
        return granteeFirstName;
    }

    public void setGranteeFirstName(String granteeFirstName) {
        this.granteeFirstName = granteeFirstName;
    }

    public String getGranteeLastName() {
        return granteeLastName;
    }

    public void setGranteeLastName(String granteeLastName) {
        this.granteeLastName = granteeLastName;
    }

    public String getLockTitle() {
        return lockTitle;
    }

    public void setLockTitle(String lockTitle) {
        this.lockTitle = lockTitle;
    }

    public String getLockLocation() {
        return lockLocation;
    }

    public void setLockLocation(String lockLocation) {
        this.lockLocation = lockLocation;
    }
}
