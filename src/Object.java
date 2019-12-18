
public class Object {
	public Object(int x, int y, String colour, String type) {
		this.posX = x;
		this.posY = y;
		this.colour = colour;
		this.type = type;
	}
	public Object(String type) {
		this.type = type;
	}
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	public String getColour() {
		return colour;
	}
	public void setColour(String colour) {
		this.colour = colour;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	int posX;
	int posY;			
	String colour;		
	String type; 		//ellipse or rectangle
}
