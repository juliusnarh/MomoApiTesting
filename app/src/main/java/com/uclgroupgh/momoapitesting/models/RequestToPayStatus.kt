package com.uclgroupgh.momoapitesting.models

class RequestToPayStatus {
    /**
     * amount : string
     * currency : string
     * financialTransactionId : string
     * externalId : string
     * payer : {"partyIdType":"MSISDN","partyId":"string"}
     * payerMessage : string
     * payeeNote : string
     * status : PENDING
     * reason : {"code":"PAYEE_NOT_FOUND","message":"string"}
     */
    var amount: String? = null
    var currency: String? = null
    var financialTransactionId: String? = null
    var externalId: String? = null
    var payer: PayerBean? = null
    var payerMessage: String? = null
    var payeeNote: String? = null
    var status: String? = null
    var reason: ReasonBean? = null

    class PayerBean {
        /**
         * partyIdType : MSISDN
         * partyId : string
         */
        var partyIdType: String? = null
        var partyId: String? = null

    }

    class ReasonBean {
        /**
         * code : PAYEE_NOT_FOUND
         * message : string
         */
        var code: String? = null
        var message: String? = null
    }
}
