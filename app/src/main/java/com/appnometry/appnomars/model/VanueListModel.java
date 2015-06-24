package com.appnometry.appnomars.model;

public class VanueListModel {

	private String venue_name;
	private String city_name;
	private String id;
	private boolean selected;

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getVenue_name() {
		return venue_name;
	}

	public void setVenue_name(String venue_name) {
		this.venue_name = venue_name;
	}

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "VanueListModel{" +
				"venue_name='" + venue_name + '\'' +
				", city_name='" + city_name + '\'' +
				", id='" + id + '\'' +
				'}';
	}
}
