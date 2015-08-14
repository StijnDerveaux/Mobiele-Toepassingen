package db;

public class DbFactory {
	 private String type;

	    public DbFactory() {
	        type = "map";
	    }

	    public Db Create(String type) {
	        Db databank = null;

	        if (type.equals("map")) {
	            databank = new MapDb();
	        }

	        return databank;
	    }
}
