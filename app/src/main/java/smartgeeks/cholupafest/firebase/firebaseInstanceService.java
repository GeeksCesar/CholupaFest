package smartgeeks.cholupafest.firebase;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import smartgeeks.cholupafest.R;

/**
 * Created by CesarLiizcano on 27/09/2017.
 */

public class firebaseInstanceService extends FirebaseInstanceIdService {

    String recent_token;
    SharedPreferences preferences;
    SharedPreferences.Editor editor ;

    @Override
    public void onTokenRefresh() {
        recent_token = FirebaseInstanceId.getInstance().getToken();

        preferences = getSharedPreferences(getString(R.string.FCM_PREFERENCE), Context.MODE_PRIVATE);
        editor  = preferences.edit();

        editor.putString(getString(R.string.FCM_TOKEN), recent_token);

        editor.commit();
    }

}
