package com.tobeto.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tobeto.business.abstracts.ShelfService;
import com.tobeto.core.utilities.config.mappers.ModelMapperService;
import com.tobeto.dto.PageResponse;
import com.tobeto.dto.PaginationRequest;
import com.tobeto.dto.SearchRequest;
import com.tobeto.dto.SuccessResponse;
import com.tobeto.dto.shelf.CreateShelfRequest;
import com.tobeto.dto.shelf.DeleteShelfRequest;
import com.tobeto.dto.shelf.GetAllShelfResponse;
import com.tobeto.dto.shelf.UpdateShelfRequest;
import com.tobeto.entities.concretes.Shelf;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/shelf")
public class ShelvesController {

	@Autowired
	private ShelfService shelfService;

	@Autowired
	private ModelMapperService modelMapper;

	@PostMapping("/create")
	public SuccessResponse create(@Valid @RequestBody CreateShelfRequest request) {
		shelfService.create(modelMapper.forRequest().map(request, Shelf.class));
		return new SuccessResponse();
	}

	@PutMapping("/update")
	public SuccessResponse update(@Valid @RequestBody UpdateShelfRequest request) {
		Shelf shelf = modelMapper.forRequest().map(request, Shelf.class);
		shelfService.update(shelf);
		return new SuccessResponse();
	}

	@PostMapping("/delete")
	public SuccessResponse delete(@Valid @RequestBody DeleteShelfRequest request) {
		shelfService.delete(request.getId());
		return new SuccessResponse();
	};

	@GetMapping("/getall")
	public PageResponse<GetAllShelfResponse> getAll() {
		PageResponse<Shelf> shelfPage = shelfService.getAll();
		List<GetAllShelfResponse> responseList = shelfPage.getData().stream()
				.map(shelf -> modelMapper.forResponse().map(shelf, GetAllShelfResponse.class)).toList();
		return new PageResponse<>(shelfPage.getCount(), responseList);
	}

	@PostMapping("/getallByPage")
	public PageResponse<GetAllShelfResponse> getAllShelfByPage(@Valid @RequestBody PaginationRequest request) {
		PageResponse<Shelf> shelfPage = shelfService.getAllByPage(request.getPageNo(), request.getPageSize());
		List<GetAllShelfResponse> responseList = shelfPage.getData().stream()
				.map(shelf -> modelMapper.forResponse().map(shelf, GetAllShelfResponse.class)).toList();
		return new PageResponse<>(shelfPage.getCount(), responseList);
	}

	@PostMapping("/search")
	public PageResponse<GetAllShelfResponse> searchShelf(@RequestBody SearchRequest request) {
		PageResponse<Shelf> shelfPage = shelfService.searchItem(request.getKeyword());
		List<GetAllShelfResponse> responseList = shelfPage.getData().stream()
				.map(shelve -> modelMapper.forResponse().map(shelve, GetAllShelfResponse.class)).toList();
		return new PageResponse<GetAllShelfResponse>(shelfPage.getCount(), responseList);
	}
}
