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
//	@PostMapping("/create")
//	public SuccessResponseDTO create(@RequestBody CreateShelfRequest request) {
//		shelfService.create(request.getCapacity(), request.getCount());
//		return new SuccessResponseDTO();
//	}
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
	public List<GetAllShelfResponse> getAll() {
		List<Shelf> shelves = shelfService.getAll();
		return shelves.stream().map(shelf -> modelMapper.forResponse().map(shelf, GetAllShelfResponse.class)).toList();
	}

	@GetMapping("/getallByPage")
	public List<GetAllShelfResponse> getAllProductsByPage(@RequestParam(defaultValue = "1") int pageNo,
			@RequestParam(defaultValue = "2") int pageSize) {
		List<Shelf> shelfPage = shelfService.getAllByPage(pageNo, pageSize);
		return shelfPage.stream().map(product -> modelMapper.forResponse().map(product, GetAllShelfResponse.class))
				.toList();
	}
}
