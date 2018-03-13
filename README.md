
# Conductors

So. You have an Orchestrator but you're one of those tech weirdies who doesn't like UIs. Maybe you're an exclusively backdoor kinda person whose chin drips with drool at the sight of a shapely API. That's between you and your shrink. But while you figure it all out, here are some wrappers and examples to help you get off

the ground with the Orchestrator API. Try to contain your excitement.

To use:
1. Select whichever language makes your eyes twinkle
2. Insert the *orchestrator* file into your pwd
3. Mimic the sample calls made in the *test* file
(No need to authenticate. This happens automatically)
***


<h2 align="center">Java + GSON</h2>

#### Connect to Orchestrator
```java
Orchestrator orch = new Orchestrator("tenant", "user", "password");
```

#### Make Calls
```java
Map res;
			
// GET
res = orch.request("get", "odata/Environments", null);
System.out.println(res);
						
// POST
JsonObject body = new JsonObject();
	   body.addProperty("Name", "Caesar");
	   body.addProperty("ValueScope", "Global");
	   body.addProperty("ValueType", "Text");
	   body.addProperty("StringValue", "Et tu asset 2");
res = orch.request("post", "odata/Assets", body.toString());
System.out.println(res);

/*See Test file for imports and error handling*/
```


<h2 align="center">Javascript</h2>

#### Connect to Orchestrator
```javascript
require('./orchestrator.js');

var orch = new Orchestrator("tenant", "username", "password");
```

#### Make Calls
```javascript
// GET
orch.request({ type: "GET", 
               extension: 'odata/Environments',
               callback: printResult });

// POST
orch.request({ type: "POST", 
               extension: 'odata/Assets',
               body: JSON.stringify({ Name: "Caesar", ValueScope: "Global" }),
               callback: printResult });

// Callback
function printResult(response) {
	console.log(response);
}
```


<h2 align="center">Python</h2>

#### Connect to Orchestrator
```python
from orchestrator import Orchestrator

orch = Orchestrator("tenant", "username", "password")
```

#### Make Calls
```python
# GET
res = orch.request('get', 'odata/Environments')
print(res)

# POST
res = orch.request('post', 'odata/Assets', {'Name': "Caesar",
					    'ValueScope': "Global",
					    'ValueType': "Text",
  					    'StringValue': "Et tu asset 2"})
print(res)
```


<h2 align="center">Ruby</h2>

#### Connect to Orchestrator
```ruby
require_relative 'orchestrator'

orch = Orchestrator.new("tenant", "user", "password")
```

#### Make Calls
```ruby
# GET
response = orch.request('get', 'odata/Environments')
puts response["body"]["value"]

# POST
response = orch.request('post', 'odata/Assets', {Name: "Caesar",
                                                 ValueScope: "Global",
                                                 ValueType: "Text",
                                                 Value: "Et tu asset 2",
                                                 StringValue: "Et tu asset 2"})
puts response["body"]
```



<h2 align="center">:rocket: Postman :rocket:</h2>
As a bonus, here's a Postman collection that automatically authenticates calls and refreshes the token before it expires.

To use:

1. Go to Import>Import from link and add https://www.getpostman.com/collections/21ccbf63948df8ae7a84
2. Create a new Environment with the following variables: `url`, `tenancyName`, `usernameOrEmailAddress`, and `password`, matching each to your Orchestrator credentials. Your calls will now be authenticated automatically.
