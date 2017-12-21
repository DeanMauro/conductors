'use strict';
require('./orchestrator.js');

/*________________________________________________________________*/
/*                    CREATE ORCHESTRATOR OBJECT                  */
/*                  (Automatically authenticates)                 */
/*              Params: tenant, username, password, [url]         */
/*________________________________________________________________*/

var orch = new Orchestrator("tenant", "username", "password");

/*________________________________________________________________*/
/*                           SEND REQUEST                         */
/* Params (Hash): request type, url extension, [data], [callback] */
/*________________________________________________________________*/

// GET (Get assets)
orch.request({ type: "GET", 
	       extension: 'odata/Assets',
	       callback: printResult });

// POST (Create new queue)
let body = JSON.stringify({Name: Math.random().toString(36).substring(2, 15), MaxNumberOfRetries: 2});
orch.request({ type: "POST",
	       extension: 'odata/QueueDefinitions',
	       body: body,
	       callback: printResult });

/*________________________________________________________________*/
/*                           GET RESPONSE                         */
/*                         Params: response                       */
/*________________________________________________________________*/

function printResult(response) {
	console.log(response);
}
