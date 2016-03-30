package shared.communication;

public class GetSampleImage_Result {
	private String url;
	public GetSampleImage_Result(String url){
		this.url=url;
	}
	public String getURL(){
		return url;
	}
}
