package com.appnometry.appnomars.model;

public class GlobalItemListModel {

	private String title;
	private String description;
	private String image;
	private String id;
	private String valid_to;
	private String valid_from;
	private String item_type;
	private String item_category;
	private String venue_name;
	private String city_name;
	private String item_venues;
	private String latitude;
	private String longitude;
	private String item_price;
	private String item_deal_price;
	private String tickets;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValid_to() {
		return valid_to;
	}

	public void setValid_to(String valid_to) {
		this.valid_to = valid_to;
	}

	public String getValid_from() {
		return valid_from;
	}

	public void setValid_from(String valid_from) {
		this.valid_from = valid_from;
	}

	public String getItem_type() {
		return item_type;
	}

	public void setItem_type(String item_type) {
		this.item_type = item_type;
	}

	public String getItem_category() {
		return item_category;
	}

	public void setItem_category(String item_category) {
		this.item_category = item_category;
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

	public String getItem_venues() {
		return item_venues;
	}

	public void setItem_venues(String item_venues) {
		this.item_venues = item_venues;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getItem_price() {
		return item_price;
	}

	public void setItem_price(String item_price) {
		this.item_price = item_price;
	}

	public String getItem_deal_price() {
		return item_deal_price;
	}

	public void setItem_deal_price(String item_deal_price) {
		this.item_deal_price = item_deal_price;
	}

	public String getTickets() {
		return tickets;
	}

	public void setTickets(String tickets) {
		this.tickets = tickets;
	}

	@Override
	public String toString() {
		return "NewsFeedModel{" +
				"title='" + title + '\'' +
				", description='" + description + '\'' +
				", image='" + image + '\'' +
				", id='" + id + '\'' +
				", valid_to='" + valid_to + '\'' +
				", valid_from='" + valid_from + '\'' +
				", item_type='" + item_type + '\'' +
				", item_category='" + item_category + '\'' +
				", venue_name='" + venue_name + '\'' +
				", city_name='" + city_name + '\'' +
				", item_venues='" + item_venues + '\'' +
				", latitude='" + latitude + '\'' +
				", longitude='" + longitude + '\'' +
				", item_price='" + item_price + '\'' +
				", item_deal_price='" + item_deal_price + '\'' +
				", tickets='" + tickets + '\'' +
				'}';
	}
}
