package com.tobeto.business.abstracts;

import java.util.List;
import java.util.UUID;

public interface BaseService<T> {
	T create(T entity);

	List<T> getAll();

	void delete(UUID id);

	void update(T entity);
}
