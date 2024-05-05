package com.tobeto.api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tobeto.business.abstracts.ShelfService;
import com.tobeto.core.utilities.config.mappers.ModelMapperService;
import com.tobeto.dto.SuccessResponse;
import com.tobeto.dto.shelf.CreateShelfRequest;
import com.tobeto.dto.shelf.GetAllShelfResponse;
import com.tobeto.dto.shelf.UpdateShelfRequest;
import com.tobeto.entities.concretes.PageResponse;
import com.tobeto.entities.concretes.Shelf;

@RestController
@RequestMapping("/api/v1/shelf")
public class ShelvesController {

	@Autowired
	private ShelfService shelfService;

	@Autowired
	private ModelMapperService modelMapper;

	/**********************************************************************/
	/**********************************************************************/
	@PostMapping("/create")
	public SuccessResponse create(@RequestBody CreateShelfRequest request) {
		shelfService.create(modelMapper.forRequest().map(request, Shelf.class));
		return new SuccessResponse();
	}

	/**********************************************************************/
	/**********************************************************************/
	@PutMapping("/update")
	public SuccessResponse update(@RequestBody UpdateShelfRequest request) {
		Shelf shelf = modelMapper.forRequest().map(request, Shelf.class);
		shelfService.update(shelf);
		return new SuccessResponse();
	}

	/**********************************************************************/
	/**********************************************************************/
	@PostMapping("/delete")
	public SuccessResponse delete(@RequestBody UUID id) {
		shelfService.delete(id);
		return new SuccessResponse();
	};

	/**********************************************************************/
	/**********************************************************************/
	@GetMapping("/getall")
	public PageResponse<GetAllShelfResponse> getAll() {
		PageResponse<Shelf> shelfPage = shelfService.getAll();
		List<GetAllShelfResponse> responseList = shelfPage.getData().stream()
				.map(shelf -> modelMapper.forResponse().map(shelf, GetAllShelfResponse.class)).toList();
		return new PageResponse<>(shelfPage.getCount(), responseList);
	}

	@GetMapping("/getallByPage")
	public PageResponse<GetAllShelfResponse> getAllProductsByPage(@RequestParam(defaultValue = "1") int pageNo,
			@RequestParam(defaultValue = "15") int pageSize) {
		PageResponse<Shelf> shelfPage = shelfService.getAllByPage(pageNo, pageSize);
		List<GetAllShelfResponse> responseList = shelfPage.getData().stream()
				.map(shelf -> modelMapper.forResponse().map(shelf, GetAllShelfResponse.class)).toList();
		return new PageResponse<>(shelfPage.getCount(), responseList);
	}

	@GetMapping("/search")
	public List<GetAllShelfResponse> searchShelf(@RequestParam String keyword) {
		List<Shelf> shelves = shelfService.searchItem(keyword);
		return shelves.stream().map(shelve -> modelMapper.forResponse().map(shelve, GetAllShelfResponse.class))
				.toList();
	}
}
