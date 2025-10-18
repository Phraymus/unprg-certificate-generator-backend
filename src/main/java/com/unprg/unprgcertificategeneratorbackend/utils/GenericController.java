package com.unprg.unprgcertificategeneratorbackend.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public abstract class GenericController<T, K> {

	public abstract GenericCrud<T, K> getCrud();

	@PostMapping
	public ResponseEntity<ApiResponse> insert(@RequestBody T t) {
		getCrud().insert(t);
		URI location = getLocation(t);
		return ResponseEntity.created(location).body(ApiResponse.ok("Se registró correctamente", t));
	}

	@PutMapping
	public ResponseEntity<ApiResponse> update(@RequestBody T t) {
		getCrud().update(t);
		return ResponseEntity.ok(ApiResponse.ok("Se actualizó correctamente", t));
	}

	@DeleteMapping
	public ResponseEntity<ApiResponse> delete(@RequestBody T t) {
		getCrud().delete(t);
		return ResponseEntity.ok(ApiResponse.ok("Se eliminó correctamente", t));
	}

	protected abstract K getPK(T t);

	protected URI getLocation(T t) {
		return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(getPK(t)).toUri();
	}
}
