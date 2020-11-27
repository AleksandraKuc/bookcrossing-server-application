package project.bookcrossing.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;

@Entity
@SequenceGenerator(name = "image_seq", allocationSize = 100)
public class Images implements Serializable {

	public Images() {
		super();
	}

	public Images(String name, String type, byte[] picByte) {
		this.name = name;
		this.type = type;
		this.picByte = picByte;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id_image;

	@Column(name = "name")
	private String name;

	@Column(name = "type")
	private String type;

	//image bytes can have large lengths so we specify a value
	//which is more than the default length for picByte column
	@Column(name = "picByte", length = 1000)
	private byte[] picByte;

	public Long getId_image() {
		return id_image;
	}

	public void setId_image(Long id_image) {
		this.id_image = id_image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getPicByte() {
		return picByte;
	}

	public void setPicByte(byte[] picByte) {
		this.picByte = picByte;
	}

	@Override
	public String toString() {
		return "Images{" +
				"id_image=" + id_image +
				", name='" + name + '\'' +
				", type='" + type + '\'' +
				", picByte=" + Arrays.toString(picByte) +
				'}';
	}
}
