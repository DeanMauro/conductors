"use strict";
var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;

class Orchestrator {
  
  constructor(tenant, user, pass, url) {
  	this.url = url || 'https://platform.uipath.com/';
  	Orchestrator.token = Orchestrator.token || this.getToken(tenant, user, pass);
  }


  getToken(tenant, user, pass) {
  	let body = JSON.stringify({tenancyName: tenant, usernameOrEmailAddress: user, password: pass});
  	return this.request({ type: "POST", 
  						  extension: 'api/account/authenticate', 
  						  body: body });
  }


  request(p) {
  	var xhttp = new XMLHttpRequest();
  	xhttp.withCredentials = true;

  	// All but authentication is asynchronous. Use a callback to get the response
  	if (!!Orchestrator.token) {
	  	xhttp.onreadystatechange = function() {
	        if (this.readyState == this.DONE && this.status < 300) {
	        	let result = JSON.parse(this.responseText);
	        	p["callback"](result);
			}
	    };
	}

	// Compose request
    xhttp.open(p["type"].toUpperCase(), this.url + p["extension"], !!Orchestrator.token);
    xhttp.setRequestHeader('Content-Type', 'application/json');
  	xhttp.setRequestHeader('Authorization', 'Bearer ' + (Orchestrator.token || ''));
    xhttp.send(p["body"]);

    // Authentication is synchronous, so just return the token.
    if (!Orchestrator.token) {
    	let arr = JSON.parse(xhttp.responseText);
    	return arr["result"]
    }
  }

}

global.Orchestrator = Orchestrator;