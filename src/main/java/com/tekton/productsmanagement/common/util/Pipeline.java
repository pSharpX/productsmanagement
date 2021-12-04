/* (C)2021 */
package com.tekton.productsmanagement.common.util;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Pipeline<I, O> {

	private final Handler<I, O> currentHandler;

	public <K> Pipeline<I, K> addHandler(Handler<O, K> newHandler) {
		return new Pipeline<>(input -> newHandler.process(this.currentHandler.process(input)));
	}

	public O execute(I input) {
		return this.currentHandler.process(input);
	}
}
