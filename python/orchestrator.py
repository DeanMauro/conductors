import requests

class Orchestrator:
	token = None

	def __init__(self, tenant, user, password, url = 'https://platform.uipath.com/'):
		self.url = url
		self.token = self.__getToken(tenant, user, password)


	def __getToken(cls, tenant, user, password):
		res = cls.request('POST', 'api/account/authenticate', {'tenancyName': tenant,
                                                                       'usernameOrEmailAddress': user, 
                                                                       'password': password})
		return res["result"]


	def request(self, type, extension, body = None):
		uri = self.url + extension
		headers = {'Authorization': 'Bearer ' + (self.token or '')}

		response = requests.request(type.upper(), uri, data=body, headers=headers)
		return response.json()
