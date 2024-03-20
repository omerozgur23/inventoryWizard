package com.tobeto.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tobeto.business.abstracts.ShelfService;
import com.tobeto.core.utilities.config.mappers.ModelMapperService;
import com.tobeto.dto.SuccessReponseDTO;
import com.tobeto.dto.shelf.CreateShelfRequest;
import com.tobeto.dto.shelf.GetAllShelfResponse;
import com.tobeto.entities.concretes.Shelf;

@RestController
@RequestMapping("/api/v1/shelf")
public class ShelfsController {

	@Autowired
	private ShelfService shelfService;

	@Autowired
	private ModelMapperService modelMapper;

	/**********************************************************************/
	/**********************************************************************/
	/**********************************************************************/
	@GetMapping("/getall")
	public ResponseEntity<List<GetAllShelfResponse>> getAll() {
		List<Shelf> shelfs = shelfService.getAll();
		List<GetAllShelfResponse> result = new ArrayList<>();
		shelfs.forEach(shelf -> {
			result.add(modelMapper.forResponse().map(shelf, GetAllShelfResponse.class));
		});
		return ResponseEntity.ok(result);
	}

	@PostMapping("/create")
	public SuccessReponseDTO create(@RequestBody CreateShelfRequest request) {

		Shelf shelf = modelMapper.forRequest().map(request, Shelf.class);
		shelfService.create(shelf);
		return new SuccessReponseDTO();
	}

}
