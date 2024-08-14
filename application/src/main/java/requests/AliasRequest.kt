package requests

import requests.Request

class AliasRequest constructor(
    var aliasType: String,
    var aliasValue: String,
    override var comments: String? = null
) : Request {
}