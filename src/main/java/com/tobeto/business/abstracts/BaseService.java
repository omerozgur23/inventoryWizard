package com.tobeto.business.abstracts;

import java.util.List;
import java.util.UUID;

public interface BaseService<T> {

	T create(T entity);

	T update(T entity);

	void delete(UUID id);

	List<T> getAll();

	List<T> getAllByPage(int pageNo, int pageSize);

	List<T> searchItem(String keyword);
}
