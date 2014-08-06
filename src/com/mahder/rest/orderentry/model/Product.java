/**
 * 
 */
package com.mahder.rest.orderentry.model;

/**
 * @author alemayehu
 *
 */
public class Product {
	private int id;
	private String name;
	private float cost;

	public Product() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

}// end class
