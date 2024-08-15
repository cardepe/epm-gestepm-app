package com.epm.gestepm.modelapi.expense.dto;

public class FileDTO {

	private String ext;
	private String name;
	private byte[] content;

	public FileDTO() {
		super();
	}

	public FileDTO(String ext, String name, byte[] content) {
		super();
		this.ext = ext;
		this.name = name;
		this.content = content;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

}
