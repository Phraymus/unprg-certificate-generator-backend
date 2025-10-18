package com.unprg.unprgcertificategeneratorbackend.utils;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.time.LocalDateTime;

import static com.unprg.unprgcertificategeneratorbackend.utils.TypeException.E;


@Data
public class ApiResponse {

	private Boolean error;
	private String codigo;
	private String titulo;
	private String mensaje;
	private TypeException type;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime fecha = LocalDateTime.now();
	private Object data;

	public ApiResponse() {
	}

	public static ApiResponse ok(String mensaje, Object data) {
		return ok("", mensaje, data);
	}

	public static ApiResponse ok(String titulo, String mensaje, Object data) {
		return load(false, titulo, mensaje, "200", data, null);
	}

	public static ApiResponse create(String mensaje, Object data) {
		return create("", mensaje, data);
	}

	public static ApiResponse create(String titulo, String mensaje, Object data) {
		return load(false, titulo, mensaje, "201", data, null);
	}

	public static ApiResponse error(String mensaje, String codigo, TypeException type) {
		return ApiResponse.error("", mensaje, codigo, type);
	}

	public static ApiResponse error(String titulo, String mensaje, String codigo, TypeException type) {
		return load(true, titulo, mensaje, codigo, null, type);
	}

	public static ApiResponse load(boolean isError, String titulo, String mensaje, String codigo, Object data,
			TypeException type) {
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setError(isError);
		apiResponse.setCodigo(codigo);
		apiResponse.setTitulo(titulo);
		apiResponse.setMensaje(mensaje);
		apiResponse.setData(data);
		apiResponse.setType(type);
		return apiResponse;
	}

	public static ApiResponse error(String mensaje) {
		return ApiResponse.error(mensaje, "500", E);
	}
}
