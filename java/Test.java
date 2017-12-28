import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class Test {

	public static void main(String[] args) {
		try {
			
			/*________________________________________________________________*/
			/*                    CREATE ORCHESTRATOR OBJECT                  */
			/*                  (Automatically authenticates)                 */
			/*              Params: tenant, username, password, [url]         */
			/*________________________________________________________________*/
			
			
			Orchestrator orch = new Orchestrator("tenant", "user", "password");
			
			
			/*________________________________________________________________*/
			/*                           SEND REQUEST                         */
			/* Params (Hash): request type, url extension, [data], [callback] */
			/*________________________________________________________________*/
			
			
			Map res;
			
			// GET
			res = orch.request("get", "odata/Environments", null);
			System.out.println(res);
			
			
			// PUT
			JsonObject body = new JsonObject();
				   body.addProperty("Name", "Asset "+UUID.randomUUID().toString().substring(0,8));
				   body.addProperty("ValueScope", "Global");
				   body.addProperty("ValueType", "Text");
				   body.addProperty("StringValue", "Et tu asset 2");
			res = orch.request("post", "odata/Assets", body.toString());
			System.out.println(res);
			
			
		} catch (AuthenticationException | IOException | JsonSyntaxException e) { e.printStackTrace(); }
	}
}
