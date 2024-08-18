package requests

import requests.Request

open class AliasRequest (
    var aliasType: String,
    var aliasValue: String,
    override var comments: String? = null
) : Request {
}