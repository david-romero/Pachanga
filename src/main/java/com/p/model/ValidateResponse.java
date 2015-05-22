package com.p.model;

import java.io.Serializable;

public class ValidateResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8120216015246736119L;
	private String shoot_conditions;
	private Boolean conditions_ok;
	private String error;

	public ValidateResponse() {
		super();
	}

	public ValidateResponse(String shoot_conditions, Boolean conditions_ok,
			String error) {
		super();
		this.shoot_conditions = shoot_conditions;
		this.conditions_ok = conditions_ok;
		this.error = error;
	}

	public String getShoot_conditions() {
		return shoot_conditions;
	}

	public void setShoot_conditions(String shoot_conditions) {
		this.shoot_conditions = shoot_conditions;
	}

	public Boolean getConditions_ok() {
		return conditions_ok;
	}

	public void setConditions_ok(Boolean conditions_ok) {
		this.conditions_ok = conditions_ok;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "ValidateResponse [shoot_conditions=" + shoot_conditions
				+ ", conditions_ok=" + conditions_ok + ", error=" + error + "]";
	}

}
