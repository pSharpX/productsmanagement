/* (C)2021 */
package com.tekton.productsmanagement.integration.util;

import java.net.URI;
import java.net.URL;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public final class ThirdPartyApiUtils {

	private static final Logger LOG = LoggerFactory.getLogger(ThirdPartyApiUtils.class);

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
			LOG.error("There was an error when building URL", ex);
		}
		return  null;
	}
}
