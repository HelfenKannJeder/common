package de.helfenkannjeder.common.identityprovider.domain;

public enum IdentityStatus {
    ACTIVE("active"),
    INACTIVE("inactive");

    private final String apiName;

    IdentityStatus(String apiName) {
        this.apiName = apiName;
    }

    public static IdentityStatus getByApiName(String apiName) {
        for (IdentityStatus identityStatus : values()) {
            if (identityStatus.apiName.equalsIgnoreCase(apiName)) {
                return identityStatus;
            }
        }
        return null;
    }

    public String getApiName() {
        return apiName;
    }
}
