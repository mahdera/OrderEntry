/**
 * 
 */
package com.mahder.rest.orderentry.model;

/**
 * @author alemayehu
 *
 */
public class LineItem {
	private int id;
	private int quantity;
	private int productId;

	public LineItem() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

}// end class
