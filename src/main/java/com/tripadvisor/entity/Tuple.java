package com.tripadvisor.entity;

// I would make this an Entity if I want to save these values in DB and retrieve information later
public class Tuple {
	
	private String file1Name;
	private String file2Name;
	private String synonymsFileName;
	private int size;
	
	public Tuple(String synonymsFileName, String file1Name, String file2Name, int size){
		this.synonymsFileName = synonymsFileName;
		this.file1Name = file1Name;
		this.file2Name = file2Name;
		this.size = size;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public boolean isValidTupleSizeForFile(int fileSize) {
		if(fileSize<size) {
			return false;
		}
		return true;
	}

	public String getFile1Name() {
		return file1Name;
	}

	public void setFile1Name(String file1Name) {
		this.file1Name = file1Name;
	}

	public String getFile2Name() {
		return file2Name;
	}

	public void setFile2Name(String file2Name) {
		this.file2Name = file2Name;
	}

	public String getSynonymsFileName() {
		return synonymsFileName;
	}

	public void setSynonymsFileName(String synonymsFileName) {
		this.synonymsFileName = synonymsFileName;
	}

}
