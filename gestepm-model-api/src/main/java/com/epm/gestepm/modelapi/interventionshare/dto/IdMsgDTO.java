package com.epm.gestepm.modelapi.interventionshare.dto;

public class IdMsgDTO {

	private Long id;
	private String msg;

	public IdMsgDTO() {
		super();
	}

	public IdMsgDTO(Long id, String msg) {
		super();
		this.id = id;
		this.msg = msg;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	

}
