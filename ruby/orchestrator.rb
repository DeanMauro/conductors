require 'uri'
require 'net/http'
require 'json'

class Orchestrator  

  def initialize(tenant, user, pass, url = 'https://platform.uipath.com/') 
    @url = url
    @token = getToken(tenant, user, pass)
  end



  def getToken(tenant, user, pass)
  	res = request('post', 'api/account/authenticate', tenancyName: tenant,
						                                          usernameOrEmailAddress: user, 
						                                          password: pass)

  	res['message'] == 'OK' ? (res['body']['result'] || res['body']['Result']) : (raise "Couldn't Authorize. Check credentials and try again.")
  end



  def request(type, extension, body = nil)
  	# Form URI
  	uri = URI(@url + extension)
    http = Net::HTTP.new(uri.host, uri.port)
    http.use_ssl = true

    # Cover Possible Params
    params = [uri]
    params << body.to_json unless body.nil?
    params << {'Content-Type' =>'application/json', 
               'Authorization' => 'Bearer ' + (@token || '')}
  	
  	# Send Request
    res = http.send(type, *params)
    return {'code' => res.code, 'message' => res.message, 'class' => res.class.name, 'body' => JSON.parse(res.body)}

  rescue Net::OpenTimeout, JSON::ParserError, SocketError
    raise "Connection/Network Error. Try again."
  end
  
end  