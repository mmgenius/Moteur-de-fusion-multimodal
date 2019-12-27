
public class FusionObject {
	public FusionObject(int x, int y, String colour, String type) {
		this.posX = x;
		this.posY = y;
		this.colour = colour;
		this.type = type;
	}
	public FusionObject(String type) {
		this();
		this.type = type;
		
	}
	public FusionObject() {
		//define default props in case stuff is not set.
		this.posX = 0;
		this.posY = 0;
		this.colour = "white";
		
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
