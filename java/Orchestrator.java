import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class Orchestrator {
	private String url;
	private static String token = "";
	
	public Orchestrator(String tenant, String user, String pass) throws AuthenticationException {
		this(tenant, user, pass, "https://platform.uipath.com/");
	}
	
	public Orchestrator(String tenant, String user, String pass, String url) throws AuthenticationException {
		this.url = url;
		token = getToken(tenant, user, pass);
	}
	
	
	private String getToken(String tenant, String user, String pass) throws AuthenticationException {
		// Form body of request
		JsonObject body = new JsonObject();
		body.addProperty("tenancyName", tenant);
		body.addProperty("usernameOrEmailAddress", user);
		body.addProperty("password", pass);
		
		// Make request & handle errors
		Map res;
		try {
			res = request("POST", "api/account/authenticate", body.toString());
		} catch (JsonSyntaxException e) {
			throw new AuthenticationException("Response was not JSON as expected.\n" + e.getMessage());
		} catch (IOException e) {
			String message = "Something went wrong...\n";
			if(e.getMessage().contains("code: 40"))
				message = "Incorrect endpoint. Check your url.\n";
			else if(e.getMessage().contains("code: 50"))
				message = "The server didn't like our request. Check your credentials.\n";
			
			throw new AuthenticationException( message + e.getMessage() );
		}
		
		// Return auth token
		return res.get("result").toString();
	}
	
	
	public Map request(String type, String extension, String body) throws IOException, JsonSyntaxException {
		// Create Connection
		URL url = new URL(this.url + extension);
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		
		// Compose Request
		con.setRequestMethod(type.toUpperCase());
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Authorization", "Bearer "+ Orchestrator.token);
		con.addRequestProperty("User-Agent", "");
		con.setDoInput(true);
		con.setDoOutput(true);
		
		// Send Request
		if (body != null) {
			OutputStream os = con.getOutputStream();
			os.write(body.getBytes("UTF-8"));
			os.close();
		}
		
		// Get Response
		BufferedReader in = new BufferedReader(
                                    new InputStreamReader(con.getInputStream()));
		String line;
		StringBuffer content = new StringBuffer();
		while ((line = in.readLine()) != null)
		    content.append(line);
		in.close();
		con.disconnect();
		
		// Parse Response to Map
		Map map = new Gson().fromJson(content.toString(), Map.class);
		return map;
	}

	
	public static void main(String[] args) {
	}

}
