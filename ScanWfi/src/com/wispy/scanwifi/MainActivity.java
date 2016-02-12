package com.wispy.scanwifi;

import java.util.ArrayList;
import java.util.List;

import com.wispy.scanwifi.ListAdapter;
import com.wispy.scanwifi.R;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity implements OnClickListener {
	Button setWifi;
	WifiManager wifiManager;
	WifiReceiver receiverWifi;
	List<ScanResult> wifiList;
	List<String> listOfProvider;
	ListAdapter adapter;
	ListView listViwProvider;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listOfProvider = new ArrayList<String>();
		
		/*setting the resources in class*/
		listViwProvider = (ListView) findViewById(R.id.list_view_wifi);
		setWifi = (Button) findViewById(R.id.btn_wifi);
		setWifi.setText("Scan");
		setWifi.setOnClickListener(this);
		wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
		
	}
	/*Start Scan*/
	@Override
	public void onClick(View arg0) { 
		/* if wifi is OFF set it ON 
		 * and scan available wifi provider*/
		if (wifiManager.isWifiEnabled() == false) 
			wifiManager.setWifiEnabled(true);
		listViwProvider.setVisibility(ListView.VISIBLE);
		scanning();
		display();

	}
	private void scanning() {
		// wifi scaned value broadcast receiver
		receiverWifi = new WifiReceiver();
		// Register broadcast receiver
		// Broacast receiver will automatically call when number of wifi
		// connections changed
		registerReceiver(receiverWifi, new IntentFilter(
				WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
		wifiManager.startScan();
	}
	private void display() {
		
		/*setting list of all wifi provider in a List*/
		adapter = new ListAdapter(MainActivity.this, listOfProvider);
		listViwProvider.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	protected void onPause() {
		super.onPause();
		unregisterReceiver(receiverWifi);
	}

	protected void onResume() {
		super.onResume();
	}

	class WifiReceiver extends BroadcastReceiver {

		// This method call when number of wifi connections changed
		public void onReceive(Context c, Intent intent) {
			wifiList = wifiManager.getScanResults();
			/* sorting of wifi provider based on level */
			/*Collections.sort(wifiList, new Comparator<ScanResult>() {
				@Override
				public int compare(ScanResult lhs, ScanResult rhs) {
					return (lhs.level > rhs.level ? -1
							: (lhs.level == rhs.level ? 0 : 1));
				}
			});*/
			listOfProvider.clear();
			String providerName;
			for (int i = 0; i < wifiList.size(); i++) {
				/* to get SSID and BSSID of wifi provider*/
				providerName = "SSID:"+(wifiList.get(i).SSID).toString()
						+"\nBSSID:"+(wifiList.get(i).BSSID).toString()+"\nRSSI:"+(Integer.toString(wifiList.get(i).level));
				listOfProvider.add(providerName);
			}
			
		}
	}
}
