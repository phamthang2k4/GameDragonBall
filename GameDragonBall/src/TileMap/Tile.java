package TileMap;

import java.awt.image.BufferedImage;

public class Tile {
	//Trong lớp Tile này, mỗi ô gạch trong bản đồ được đại diện bởi một đối tượng Tile. 
	//Đối tượng này chứa thông tin về hình ảnh của ô gạch và loại của nó. 
	
	private BufferedImage image; // Biến này lưu trữ hình ảnh của ô gạch.
	private int type; //Biến này chỉ định loại của ô gạch (ví dụ: bình thường hoặc chặn).
	
	// tile types
	public static final int NORMAL = 0;
	public static final int BLOCKED = 1;
	
	public Tile(BufferedImage image, int type) {
		this.image = image;
		this.type = type;
	}
	
	public BufferedImage getImage() { return image; }
	public int getType() { return type; }
	
}
