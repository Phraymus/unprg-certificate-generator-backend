package com.unprg.unprgcertificategeneratorbackend.utils;

public interface GenericCrud<T, K> {

	T insert(T t);

	T update(T t);

	void delete(T t);

	T findById(K k);
}
