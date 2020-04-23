package com.netcund.app.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Dto para los clientes
 * @author sabernal
 *
 */
public class ClientDTO {
	
	private Long id;
	
	/**
	 * Nombre del cliente
	 */
	@NotNull
	@NotEmpty
	@NotBlank
	private String nombre;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	
}
