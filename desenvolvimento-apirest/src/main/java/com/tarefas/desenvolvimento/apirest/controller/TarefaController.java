package com.tarefas.desenvolvimento.apirest.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tarefas.desenvolvimento.apirest.model.Tarefa;
import com.tarefas.desenvolvimento.apirest.repository.TarefaRepository;

@RestController
@RequestMapping( "/tarefas" )
public class TarefaController {
	private final TarefaRepository repository;
	
	public TarefaController(TarefaRepository repository) {
		
		this.repository = repository;
	}

	
	@GetMapping
	public List<Tarefa> listar(){
		return repository.findAll();
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Tarefa> findById(@PathVariable long id){
		return repository.findById(id)
			.map(record -> ResponseEntity.ok().body(record))
			.orElse(ResponseEntity.notFound().build());
		}
	

	@PostMapping
	public Tarefa create (@RequestBody Tarefa tarefa) {
		return repository.save(tarefa);
	}
	
	
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Tarefa> update(@PathVariable("id") long id, @RequestBody Tarefa tarefa) {
		return repository.findById(id).map(record -> {
			record.setNome(tarefa.getNome());
			record.setDataEntrega(tarefa.getDataEntrega());
			record.setResponsavel(tarefa.getResponsavel());
			Tarefa update = repository.save(record);
			return ResponseEntity.ok().body(update);
		}).orElse(ResponseEntity.notFound().build());
			
		}
	
	@DeleteMapping (path = {"/{id}"})
	public ResponseEntity<Void> delete(@PathVariable long id){
		return repository.findById(id).map(record -> {
			repository.deleteById(id);
			return ResponseEntity.noContent().<Void>build();
		}).orElse(ResponseEntity.notFound().build());
	}
}
	


