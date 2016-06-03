package de.helfenkannjeder.common.identityprovider.domain;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum AuthenticationProvider {
	FACEBOOK("facebook"),
	GOOGLE("google"),
	HELFENKANNJEDER("helfenkannjeder");

	public static final Set<String> API_NAMES = Arrays.stream(values()).map(provider -> provider.apiName).collect(Collectors.toSet());

	private final String apiName;

	AuthenticationProvider(String apiName) {
		this.apiName = apiName;
	}

	public static AuthenticationProvider getByApiName(String apiName) {
		for (AuthenticationProvider authenticationProvider : values()) {
			if (authenticationProvider.apiName.equalsIgnoreCase(apiName)) {
				return authenticationProvider;
			}
		}
		return null;
	}

	public String getApiName() {
		return apiName;
	}
}
