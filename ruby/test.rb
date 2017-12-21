require_relative 'orchestrator'

#________________________________________________________________#
#                    CREATE ORCHESTRATOR OBJECT                  #
#                  (Automatically authenticates)                 #
#     Params: tenant, username, password, [orchestrator url]     #
#________________________________________________________________#

orch = Orchestrator.new("playground", "admin", "123qwe123")

#________________________________________________________________#
#                          SEND REQUEST                          #
#          Params: request type, url extension, [data]           #
#________________________________________________________________#

# GET
res = orch.request('get', 'odata/Environments')
puts res["body"]["value"]

# POST
res = orch.request('post', 'odata/Assets', {Name: "Asset" + Random.rand(99999).to_s,
											ValueScope: "Global",
											ValueType: "Text",
											Value: "Et tu asset 2",
  											StringValue: "Et tu asset 2"})
puts res["body"]