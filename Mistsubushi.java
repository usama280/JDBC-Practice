
public class Mistsubushi {
	private String model;
	private String type;
	private String color;
	
	public Mistsubushi(String model, String type, String color) {
		this.model = model;
		this.type = type;
		this.color = color;
	}
	
	@Override
	public String toString() {
		return "Mistsubushi [model=" + model + ", type=" + type + ", color=" + color + "]";
	}

	public boolean equals(Object o){
     if(o==null) {
    	 return false;
     }
     else if(o==this) {
    	 return true;
     }
     else if(o instanceof Mistsubushi) {
    	 if(this.model.equals(((Mistsubushi)o).model) && this.type.equals(((Mistsubushi)o).type) && this.color.equals(((Mistsubushi)o).color)) {
    		 return true;
    	 }
     }
    return false;
 }
	
}
