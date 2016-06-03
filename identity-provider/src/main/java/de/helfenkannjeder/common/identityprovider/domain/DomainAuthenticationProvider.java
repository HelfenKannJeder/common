package de.helfenkannjeder.common.identityprovider.domain;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum DomainAuthenticationProvider {
	FACEBOOK("facebook"),
	GOOGLE("google"),
	HELFENKANNJEDER("helfenkannjeder");

	public static final Set<String> API_NAMES = Arrays.stream(values()).map(provider -> provider.apiName.toUpperCase()).collect(Collectors.toSet());

	private final String apiName;

	DomainAuthenticationProvider(String apiName) {
		this.apiName = apiName;
	}

	public String getApiName() {
		return apiName;
	}
}
