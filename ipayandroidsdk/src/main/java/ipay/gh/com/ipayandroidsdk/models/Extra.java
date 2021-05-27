
package ipay.gh.com.ipayandroidsdk.models;

import com.google.gson.annotations.SerializedName;

public class Extra {

    @SerializedName("mobile_no")
    private String mobileNo;
    @SerializedName("channel")
    private String channel;
    @SerializedName("psp_response_code")
    private String pspResponseCode;
    @SerializedName("mtn_general_payment_id")
    private String mtnGeneralPaymentId;
    @SerializedName("email")
    private String email;
    @SerializedName("wallet_issuer_hint")
    private String walletIssuerHint;
    @SerializedName("psp_response_msg")
    private String pspResponseMsg;
    @SerializedName("src_currency")
    private String srcCurrency;
    @SerializedName("card_type")
    private String cardType;
    @SerializedName("name")
    private String name;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getPspResponseCode() {
        return pspResponseCode;
    }

    public void setPspResponseCode(String pspResponseCode) {
        this.pspResponseCode = pspResponseCode;
    }

    public String getMtnGeneralPaymentId() {
        return mtnGeneralPaymentId;
    }

    public void setMtnGeneralPaymentId(String mtnGeneralPaymentId) {
        this.mtnGeneralPaymentId = mtnGeneralPaymentId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWalletIssuerHint() {
        return walletIssuerHint;
    }

    public void setWalletIssuerHint(String walletIssuerHint) {
        this.walletIssuerHint = walletIssuerHint;
    }

    public String getPspResponseMsg() {
        return pspResponseMsg;
    }

    public void setPspResponseMsg(String pspResponseMsg) {
        this.pspResponseMsg = pspResponseMsg;
    }

    public String getSrcCurrency() {
        return srcCurrency;
    }

    public void setSrcCurrency(String srcCurrency) {
        this.srcCurrency = srcCurrency;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
