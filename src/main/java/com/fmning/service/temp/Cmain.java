package com.fmning.service.temp;

import java.time.Instant;

public class Cmain {
	private int id;
	private int ehr;
	private int phr;
	private double temp;
	private int spo2;
	private Instant createdAt;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEhr() {
		return ehr;
	}

	public void setEhr(int ehr) {
		this.ehr = ehr;
	}

	public int getPhr() {
		return phr;
	}

	public void setPhr(int phr) {
		this.phr = phr;
	}

	public double getTemp() {
		return temp;
	}

	public void setTemp(double temp) {
		this.temp = temp;
	}

	public int getSpo2() {
		return spo2;
	}

	public void setSpo2(int spo2) {
		this.spo2 = spo2;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

}
