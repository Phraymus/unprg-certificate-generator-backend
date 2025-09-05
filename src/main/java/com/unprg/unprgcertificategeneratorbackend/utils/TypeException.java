/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unprg.unprgcertificategeneratorbackend.utils;

/**
 *
 * @author Edgar
 */
public enum TypeException {

	I("Informativo"), A("Advertencia"), E("Error");

	private final String value;

	private TypeException(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

	public String getValue() {
		return value;
	}
}
