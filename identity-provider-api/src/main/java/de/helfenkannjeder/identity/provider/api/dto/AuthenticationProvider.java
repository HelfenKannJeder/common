package de.helfenkannjeder.identity.provider.api.dto;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum AuthenticationProvider {
	FACEBOOK("facebook"),
	GOOGLE("google"),
	HELFENKANNJEDER("helfenkannjeder");

	public static final Set<String> API_NAMES = Arrays.stream(values()).map(provider -> provider.apiName.toUpperCase()).collect(Collectors.toSet());

	private final String apiName;

	AuthenticationProvider(String apiName) {
		this.apiName = apiName;
	}
}
