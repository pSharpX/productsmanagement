/* (C)2021 */
package com.tekton.productsmanagement.common.util;

public interface Handler<I, O> {

	O process(I input);
}
