package com.tobeto.business.abstracts;

import java.util.UUID;

import com.tobeto.dto.PageResponse;

public interface BaseService<T> {

	T create(T entity);

	T update(T entity);

	void delete(UUID id);

	PageResponse<T> getAll();

	PageResponse<T> getAllByPage(int pageNo, int pageSize);

	PageResponse<T> searchItem(String keyword);
}
