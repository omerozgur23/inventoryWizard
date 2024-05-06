package com.tobeto.business.abstracts;

import java.util.List;
import java.util.UUID;

import com.tobeto.entities.concretes.PageResponse;

public interface BaseService<T> {

	T create(T entity);

	T update(T entity);

	void delete(UUID id);

	PageResponse<T> getAll();

	PageResponse<T> getAllByPage(int pageNo, int pageSize);

	List<T> searchItem(String keyword);
}
