package info.jwclark.intentservice;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private XYZReceiver receiver;
	Button btnStart, btnStop;
	TextView lblX, lblY, lblZ;
	Intent xyzIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnStart = (Button)findViewById(R.id.btnStart);
		btnStop = (Button)findViewById(R.id.btnStop);
		lblX = (TextView)findViewById(R.id.lblX);
		lblY = (TextView)findViewById(R.id.lblY);
		lblZ = (TextView)findViewById(R.id.lblZ);
		xyzIntent = new Intent(MainActivity.this, XYZService.class);
		
		btnStart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				XYZService.SHOULD_CONTINUE = true;
				startService(xyzIntent);
			}
		});
		
		btnStop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				XYZService.SHOULD_CONTINUE = false;
				lblX.setText("service stopped");
				lblY.setText("");
				lblZ.setText("");
			}
		});
		
		IntentFilter filter = new IntentFilter(XYZReceiver.PROCESS_RESPONSE);
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		receiver = new XYZReceiver();
		registerReceiver(receiver, filter);
	}
	
	public class XYZReceiver extends BroadcastReceiver {
		public static final String PROCESS_RESPONSE = "info.jwclark.intentservice.intent.action.PROCESS_RESPONSE";
		
		@Override
		public void onReceive(Context context, Intent intent) {
			String X = intent.getStringExtra(XYZService.X);
			String Y = intent.getStringExtra(XYZService.Y);
			String Z = intent.getStringExtra(XYZService.Z);			
			lblX.setText(X);
			lblY.setText(Y);
			lblZ.setText(Z);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
