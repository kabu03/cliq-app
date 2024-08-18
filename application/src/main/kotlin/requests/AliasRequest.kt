package requests

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import requests.Request

open class AliasRequest @JsonCreator constructor (
    @JsonProperty ("aliasType") var aliasType: String,
    @JsonProperty ("aliasValue") var aliasValue: String,
    override var comments: String? = null
) : Request {
}