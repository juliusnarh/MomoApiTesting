package ipay.gh.com.ipayandroidsdk.application;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import ipay.gh.com.ipayandroidsdk.utils.IPayUtils;

public final class IPaySDK extends Application {

    @Override
    public void onCreate() {
        super.onCreate( );
        Log.v(getClass().getName(), "Starting SDK Module");
        IPayUtils.validate.hasInternetPermission(getApplicationContext());
//        try {
//            Intent intent = new Intent(getApplicationContext(),
//                    Class.forName("ipay.gh.com.ipayandroidsdk.PaymentActivity"));
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
    }

}
