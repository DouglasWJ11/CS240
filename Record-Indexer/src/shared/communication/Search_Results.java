package shared.communication;

import java.util.List;

import shared.model.SearchTuple;

public class Search_Results {

	private List<SearchTuple> results;
	
	public Search_Results(List<SearchTuple> results){
		this.results=results;
	}
	public String toString(){
		StringBuilder sb=new StringBuilder();
		for(SearchTuple st:results){
			sb.append(st.toString());
		}
		return sb.toString();
	}
	public List<SearchTuple> getResults() {
		return results;
	}
}
