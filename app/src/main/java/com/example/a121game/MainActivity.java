package com.example.a121game;

import android.Manifest;
import android.content.IntentFilter;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.AdvertisingOptions;
import com.google.android.gms.nearby.connection.ConnectionInfo;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import com.google.android.gms.nearby.connection.ConnectionResolution;
import com.google.android.gms.nearby.connection.ConnectionsClient;
import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo;
import com.google.android.gms.nearby.connection.DiscoveryOptions;
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback;
import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.nearby.connection.PayloadCallback;
import com.google.android.gms.nearby.connection.PayloadTransferUpdate;
import com.google.android.gms.nearby.connection.PayloadTransferUpdate.Status;
import com.google.android.gms.nearby.connection.Strategy;

import com.google.android.gms.nearby.connection.ConnectionsClient;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int MY_PERMISSIONS_ACCESS_COARSE_LOCATION = 0;
    private static final String TAG = "DungeonsAndDragons";
    private static final Strategy STRATEGY = Strategy.P2P_STAR;
    private ConnectionsClient connectionsClient;
    //placeholder for player id
    private final String codeName = "simpleName";

    //should be changed to DM endpointId and DM name
    private String opponentEndpointId;
    private String opponentName;

    //arraylist for holding each player Object
    private ArrayList<player> members = new ArrayList<player>();

    private Button findDMButton;
    private Button playerButton;
    private TextView statusText;

    private final EndpointDiscoveryCallback endpointDiscoveryCallback =
            new EndpointDiscoveryCallback() {
                @Override
                public void onEndpointFound(String endpointId, DiscoveredEndpointInfo info) {
                    Log.i(TAG, "onEndpointFound: endpoint found, connecting");
                    connectionsClient.requestConnection(codeName, endpointId, connectionLifecycleCallback);
                }

                @Override
                public void onEndpointLost(String endpointId) {
                    Log.i(TAG, "endpoint lost");
                }

            };
    private final ConnectionLifecycleCallback connectionLifecycleCallback =
            new ConnectionLifecycleCallback() {
                @Override
                public void onConnectionInitiated(String endpointId, ConnectionInfo connectionInfo) {
                    Log.i(TAG, "onConnectionInitiated: accepting connection");
                    connectionsClient.acceptConnection(endpointId, payloadCallback);
                    opponentName = connectionInfo.getEndpointName();
                }

                @Override
                public void onConnectionResult(String endpointId, ConnectionResolution result) {
                    if (result.getStatus().isSuccess()) {
                        Log.i(TAG, "onConnectionResult: connection successful");

                        connectionsClient.stopDiscovery();
                        connectionsClient.stopAdvertising();
                        setStatusText(getString(R.string.status_connected));
                        opponentEndpointId = endpointId;
                    } else {
                        Log.i(TAG, "onConnectionResult: connection failed");
                    }
                }

                @Override
                public void onDisconnected(String endpointId) {
                    Log.i(TAG, "onDisconnected: disconnected from " + endpointId);
                    //need to decide what to do when disconnected from player/dm.
                    //possibly save current info on that player?
                }
            };

    private final PayloadCallback payloadCallback =
            new PayloadCallback() {
                @Override
                public void onPayloadReceived(String endpointId, Payload payload) {
                    //here will basically be converting the playload from whatever form we used to send it
                    //back into a player Object that can be added to the members ArrayList.
                }

                @Override
                public void onPayloadTransferUpdate(String endpointId, PayloadTransferUpdate update) {
                   //what we will be doing once we have added the info from the received payload to our members ArrayList
                }
            };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //if the user has not yet allowed the device to access location data, will prompt for permission
        //am currently unsure how we should handle declined permissions (probably exit app), as the connectivity api that will most likely be used
        //will require location data.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_ACCESS_COARSE_LOCATION);
        }

        findDMButton = findViewById(R.id.button2);  //create game
        playerButton = findViewById(R.id.button);  //find game
        statusText = findViewById(R.id.textView);

        connectionsClient = Nearby.getConnectionsClient(this);

    }

    //discovering DM
    public void findDM(View view){
        startDiscovery();
        setStatusText(getString(R.string.status_searching));
        findDMButton.setEnabled(false);
    }

    public void hostLobby(View view){
        startAdvertising();
        setStatusText(getString(R.string.status_waiting));
        playerButton.setEnabled(false);
    }
    //will not handle failed discovery as of yet
    private void startDiscovery() {
        connectionsClient.startDiscovery(
                getPackageName(), endpointDiscoveryCallback, new DiscoveryOptions(STRATEGY));
    }

    //will not handle an advertising failure
    private void startAdvertising() {
        connectionsClient.startAdvertising(
                codeName, getPackageName(), connectionLifecycleCallback, new AdvertisingOptions(STRATEGY));
    }
    private void setStatusText(String text) {
        statusText.setText(text);
    }
    private void resetLobby(){

    }
}
