package com.uitox.fb.entity;

import java.util.ArrayList;

public class Movie {
	private String title, image;
	private int year;
	private double rating;
	private ArrayList<String> genre;

	public Movie() {
	}

	public Movie(String name, String image, int year, double rating, ArrayList<String> genre) {
		this.title = name;
		this.image = image;
		this.year = year;
		this.rating = rating;
		this.genre = genre;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String name) {
		this.title = name;
	}

	public String getImageUrl() {
		return image;
	}

	public void setImageUrl(String thumbnailUrl) {
		this.image = thumbnailUrl;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public ArrayList<String> getGenre() {
		return genre;
	}

	public void setGenre(ArrayList<String> genre) {
		this.genre = genre;
	}

}
