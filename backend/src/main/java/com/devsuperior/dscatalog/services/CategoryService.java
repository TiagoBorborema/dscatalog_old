package com.devsuperior.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.EntityNotFoundException;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll(){
		List<Category> list = repository.findAll();
		
		// De uma forma mais fácil se aplica abaixo
		return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
			
		/* Modelo que também pode ser utilizado para retornar a lista 
		  List<CategoryDTO> listDto = new ArrayList<>();
		for(Category cat: list ) {
		  listDto.add( new CategoryDTO(cat)); 
		 }
		  */	
		
	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(long id) {
		Optional<Category> obj = repository.findById(id);
		Category entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity not Found"));
		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		repository.save(entity);
		return new CategoryDTO(entity);
	}

}
	