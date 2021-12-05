/* (C)2021 */
package com.tekton.productsmanagement.integration.util;

import java.net.URI;
import java.net.URL;
import java.util.Map;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
public final class ThirdPartyApiUtils {

	private ThirdPartyApiUtils(){}

	public static URI buildUri(String scheme, String host, String port, String path, MultiValueMap<String, String> queryParams, Map<String, Object> pathParams) {
		try {
			URL url;
			if(StringUtils.hasText(port)){
				int portNumber = Integer.parseInt(port);
				url = new URL(scheme, host, portNumber, "");
			} else {
				url = new URL(scheme, host, "");
			}

			UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath(path);
			uriBuilder.queryParams(queryParams);
			UriComponents uri = uriBuilder.build();

			if(Objects.nonNull(pathParams)) {
				uri = uri.expand(pathParams);
			}
			return new URI(url.toString().concat(uri.toUriString()));
		} catch(Exception ex) {
			log.error("There was an error when building URL", ex);
		}
		return  null;
	}
}
