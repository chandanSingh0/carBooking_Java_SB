package com.booking.request;

public class RideRequest {
	private double pickUpLatitude;
	private double pickUpLongitude;
	private double destinationLatitude;
	private double destinationLongitude;
	private String pickupArea;
	private String destinationArea;

	public RideRequest() {
		// TODO Auto-generated constructor stub
	}

	public double getPickUpLatitude() {
		return pickUpLatitude;
	}

	public void setPickUpLatitude(double pickUpLatitude) {
		this.pickUpLatitude = pickUpLatitude;
	}

	public double getPickUpLongitude() {
		return pickUpLongitude;
	}

	public void setPickUpLongitude(double pickUpLongitude) {
		this.pickUpLongitude = pickUpLongitude;
	}

	public double getDestinationLatitude() {
		return destinationLatitude;
	}

	public void setDestinationLatitude(double destinationLatitude) {
		this.destinationLatitude = destinationLatitude;
	}

	public double getDestinationLongitude() {
		return destinationLongitude;
	}

	public void setDestinationLongitude(double destinationLongitude) {
		this.destinationLongitude = destinationLongitude;
	}

	public String getPickupArea() {
		return pickupArea;
	}

	public void setPickupArea(String pickupArea) {
		this.pickupArea = pickupArea;
	}

	public String getDestinationArea() {
		return destinationArea;
	}

	public void setDestinationArea(String destinationArea) {
		this.destinationArea = destinationArea;
	}

}
