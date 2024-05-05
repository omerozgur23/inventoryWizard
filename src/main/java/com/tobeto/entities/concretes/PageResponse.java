package com.tobeto.entities.concretes;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {

	private int count;

	private List<T> data;
}
