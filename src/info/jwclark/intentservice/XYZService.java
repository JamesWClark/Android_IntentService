package info.jwclark.intentservice;

import info.jwclark.intentservice.MainActivity.XYZReceiver;

import java.util.Random;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;

public class XYZService extends IntentService {

	public static volatile boolean SHOULD_CONTINUE = false;
	public static final String X = "X";
	public static final String Y = "Y";
	public static final String Z = "Z";
	
	public XYZService() {
		super("XYZService");
	}
	@Override
	public void onHandleIntent(Intent intent) {
		while(SHOULD_CONTINUE) {
			
			String x = Float.toString(new Random().nextFloat());
			String y = Float.toString(new Random().nextFloat());
			String z = Float.toString(new Random().nextFloat());
			
			Intent broadcastIntent = new Intent();
			broadcastIntent.setAction(XYZReceiver.PROCESS_RESPONSE);
			broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
			broadcastIntent.putExtra(X, x);
			broadcastIntent.putExtra(Y, y);
			broadcastIntent.putExtra(Z, z);
			sendBroadcast(broadcastIntent);
			
			SystemClock.sleep(700);
		}

	}
}
