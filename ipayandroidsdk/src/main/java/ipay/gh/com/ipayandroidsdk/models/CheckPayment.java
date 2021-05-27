
package ipay.gh.com.ipayandroidsdk.models;

import com.google.gson.annotations.SerializedName;

public class CheckPayment {

    @SerializedName("x-response-id")
    private String xResponseId;
    @SerializedName("success")
    private boolean success;
    @SerializedName("status")
    private String status;
    @SerializedName("as_at")
    private String asAt;
    @SerializedName("last_updated")
    private String lastUpdated;
    @SerializedName("service_fee")
    private String serviceFee;
    @SerializedName("user_extra")
    private String userExtra;
    @SerializedName("extra")
    private Extra extra;
    @SerializedName("buyer_firstname")
    private Object buyerFirstname;
    @SerializedName("total_amount_charged")
    private String totalAmountCharged;
    @SerializedName("payment_instrument")
    private String paymentInstrument;
    @SerializedName("buyer_phone")
    private String buyerPhone;
    @SerializedName("status_reason")
    private String statusReason;
    @SerializedName("remittance_verification_request")
    private boolean remittanceVerificationRequest;
    @SerializedName("payment_reference")
    private String paymentReference;
    @SerializedName("service_fee_merged")
    private boolean serviceFeeMerged;
    @SerializedName("sponsor")
    private String sponsor;
    @SerializedName("client_ip_location")
    private String clientIpLocation;
    @SerializedName("tran_type")
    private String tranType;
    @SerializedName("gw_invoice_id")
    private String gwInvoiceId;
    @SerializedName("narration")
    private String narration;
    @SerializedName("invoice_id")
    private String invoiceId;
    @SerializedName("buyer_lastname")
    private Object buyerLastname;
    @SerializedName("buyer_email")
    private Object buyerEmail;
    @SerializedName("amount")
    private String amount;
    @SerializedName("client_ip")
    private String clientIp;
    @SerializedName("signed_fields")
    private String signedFields;
    @SerializedName("signature")
    private String signature;
    @SerializedName("merchant_key")
    private String merchantKey;
    @SerializedName("outlet")
    private String outlet;
    @SerializedName("IsAdminNotification")
    private boolean isAdminNotification;

    public String getxResponseId() {
        return xResponseId;
    }

    public void setxResponseId(String xResponseId) {
        this.xResponseId = xResponseId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAsAt() {
        return asAt;
    }

    public void setAsAt(String asAt) {
        this.asAt = asAt;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(String serviceFee) {
        this.serviceFee = serviceFee;
    }

    public String getUserExtra() {
        return userExtra;
    }

    public void setUserExtra(String userExtra) {
        this.userExtra = userExtra;
    }

    public Extra getExtra() {
        return extra;
    }

    public void setExtra(Extra extra) {
        this.extra = extra;
    }

    public Object getBuyerFirstname() {
        return buyerFirstname;
    }

    public void setBuyerFirstname(Object buyerFirstname) {
        this.buyerFirstname = buyerFirstname;
    }

    public String getTotalAmountCharged() {
        return totalAmountCharged;
    }

    public void setTotalAmountCharged(String totalAmountCharged) {
        this.totalAmountCharged = totalAmountCharged;
    }

    public String getPaymentInstrument() {
        return paymentInstrument;
    }

    public void setPaymentInstrument(String paymentInstrument) {
        this.paymentInstrument = paymentInstrument;
    }

    public String getBuyerPhone() {
        return buyerPhone;
    }

    public void setBuyerPhone(String buyerPhone) {
        this.buyerPhone = buyerPhone;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    public boolean isRemittanceVerificationRequest() {
        return remittanceVerificationRequest;
    }

    public void setRemittanceVerificationRequest(boolean remittanceVerificationRequest) {
        this.remittanceVerificationRequest = remittanceVerificationRequest;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }

    public boolean isServiceFeeMerged() {
        return serviceFeeMerged;
    }

    public void setServiceFeeMerged(boolean serviceFeeMerged) {
        this.serviceFeeMerged = serviceFeeMerged;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public String getClientIpLocation() {
        return clientIpLocation;
    }

    public void setClientIpLocation(String clientIpLocation) {
        this.clientIpLocation = clientIpLocation;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public String getGwInvoiceId() {
        return gwInvoiceId;
    }

    public void setGwInvoiceId(String gwInvoiceId) {
        this.gwInvoiceId = gwInvoiceId;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Object getBuyerLastname() {
        return buyerLastname;
    }

    public void setBuyerLastname(Object buyerLastname) {
        this.buyerLastname = buyerLastname;
    }

    public Object getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(Object buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getSignedFields() {
        return signedFields;
    }

    public void setSignedFields(String signedFields) {
        this.signedFields = signedFields;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getMerchantKey() {
        return merchantKey;
    }

    public void setMerchantKey(String merchantKey) {
        this.merchantKey = merchantKey;
    }

    public String getOutlet() {
        return outlet;
    }

    public void setOutlet(String outlet) {
        this.outlet = outlet;
    }

    public boolean isAdminNotification() {
        return isAdminNotification;
    }

    public void setAdminNotification(boolean adminNotification) {
        isAdminNotification = adminNotification;
    }
}
