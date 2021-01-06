package com.uclgroupgh.momoapitesting.models

class RequestToPay {
    /**
     * amount : string
     * currency : string
     * externalId : string
     * payer : {"partyIdType":"MSISDN","partyId":"string"}
     * payerMessage : string
     * payeeNote : string
     */
    var amount: String = "null"
    var currency: String = "GHS"
    var financialTransactionId: String? = null
    var externalId: String = "null"
    var payer: Payer = Payer()
    var payerMessage: String = "Paying for property rate"
    var payeeNote: String = "WULUCKY"
    var status: String? = null
    var reason: Reason? = null

     class Payer {
        /**
         * partyIdType : MSISDN
         * partyId : string
         */
        var partyIdType: String = "msisdn"
        var partyId: String = ""

    }

    class Reason {
        /**
         * code : PAYEE_NOT_FOUND
         * message : string
         */
        var code: String? = null
        var message: String? = null
    }
}
