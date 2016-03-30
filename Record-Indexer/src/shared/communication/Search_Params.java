package shared.communication;

public class Search_Params {
	private String username;
	private String password;
	private Integer[] searchFields;
	private String[] searchStrings;
	
	public Search_Params(String username, String password, String fields, String searchString){
		this.username=username;
		this.password=password;
		String delim="[,]";
		searchStrings=searchString.split(delim);
		String[] field=fields.split(delim);
		searchFields = new Integer[field.length];
		for(int i=0;i<field.length;i++){
			searchFields[i]= Integer.parseInt(field[i]);
		}
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public Integer[] getSearchFields() {
		return searchFields;
	}

	public String[] getSearchStrings() {
		return searchStrings;
	}
	
}
