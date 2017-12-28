from orchestrator import Orchestrator
import uuid

#________________________________________________________________#
#                    CREATE ORCHESTRATOR OBJECT                  #
#                  (Automatically authenticates)                 #
#     Params: tenant, username, password, [orchestrator url]     #
#________________________________________________________________#

orch = Orchestrator("tenant", "username", "password")

#________________________________________________________________#
#                          SEND REQUEST                          #
#          Params: request type, url extension, [data]           #
#________________________________________________________________#

# GET
res = orch.request('get', 'odata/Environments')
print(res)

# POST
res = orch.request('post', 'odata/Assets', {'Name': "Asset " + str(uuid.uuid4())[0:8],
					    'ValueScope': "Global",
					    'ValueType': "Text",
  					    'StringValue': "Et tu asset 2"})
print(res)
