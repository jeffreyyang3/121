package com.example.a121game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.AdvertisingOptions;
import com.google.android.gms.nearby.connection.ConnectionInfo;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import com.google.android.gms.nearby.connection.ConnectionResolution;
import com.google.android.gms.nearby.connection.ConnectionsClient;
import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo;
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback;
import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.nearby.connection.PayloadCallback;
import com.google.android.gms.nearby.connection.PayloadTransferUpdate;
import com.google.android.gms.nearby.connection.Strategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class Host extends AppCompatActivity {
    private ListView mListView;
    private Button connect;
    private Button cancel;

    public static final int MY_PERMISSIONS_ACCESS_COARSE_LOCATION = 0;
    private static final String TAG = "DungeonsAndDragons";
    private static final Strategy STRATEGY = Strategy.P2P_STAR;
    private ConnectionsClient connectionsClient;
    // //placeholder for player id
    private final String codeName = "simpleName";
    private String opponentEndpointId;
    private String opponentName;
    private String[] playerTest;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);
        connectionsClient = Nearby.getConnectionsClient(this);

        mListView = (ListView) findViewById(R.id.player_view);
        connect = (Button) findViewById(R.id.button8);

        cancel = (Button) findViewById(R.id.button9);
        cancel.setVisibility(View.GONE);
        playerTest = new String[1];
        try{

            File f = new File(getFilesDir(), "host_file.ser");
            f.createNewFile();
            FileInputStream fi = new FileInputStream(f);
            ObjectInputStream o = new ObjectInputStream(fi);

            String j = (String)o.readObject();
            JSONObject jO = new JSONObject(j);
            playerTest[0] = jO.getString("PlayerName");

            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, playerTest);
            mListView.setAdapter(adapter);
        }
        catch(Exception e)
        {
            Toast.makeText(Host.this, e.toString(),
                    Toast.LENGTH_LONG).show();
        }

    }

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
                        opponentEndpointId = endpointId;
                        Toast.makeText(Host.this, "Connection Successful.",
                                Toast.LENGTH_LONG).show();

                    } else {
                        Log.i(TAG, "onConnectionResult: connection failed");
                        Toast.makeText(Host.this, "Connection Failed.",
                                Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onDisconnected(String endpointId) {
                    Log.i(TAG, "onDisconnected: disconnected from " + endpointId);
                }
            };
    private final PayloadCallback payloadCallback =
            new PayloadCallback() {
                @Override
                public void onPayloadReceived(String endpointId, Payload payload) {
                    String receivedPlayer;
                    try{
                        receivedPlayer= new String(payload.asBytes(), "utf-8");
                        //JSONObject newPlayer = new JSONObject(receivedPlayer);
                        try{
                            File f = new File(getFilesDir(), "host_file.ser");
                            f.createNewFile();
                            FileOutputStream fo = new FileOutputStream(f);
                            ObjectOutputStream o = new ObjectOutputStream(fo);
                            o.writeObject(receivedPlayer);
                            fo.close();
                            o.close();
                        }
                        catch(Exception e)
                        {
                            Toast.makeText(Host.this, "File Write Error.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                    catch(Exception e)
                    {
                        Toast.makeText(Host.this, "Receive Byte Error.",
                                Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onPayloadTransferUpdate(String endpointId, PayloadTransferUpdate update) {
                    if((update.getStatus() == PayloadTransferUpdate.Status.SUCCESS))
                    {
                        Toast.makeText(Host.this, "Transfer Complete.",
                                Toast.LENGTH_LONG).show();
                    }
                    connectionsClient.disconnectFromEndpoint(endpointId);
                }
            };
    //will not handle an advertising failure
    public void startAdvertising(View view)
    {
        cancel.setVisibility(View.VISIBLE);
        connect.setEnabled(false);
        connectionsClient.startAdvertising(
                codeName, getPackageName(), connectionLifecycleCallback, new AdvertisingOptions(STRATEGY));
        Toast.makeText(Host.this, "Searching for Players...",
                Toast.LENGTH_LONG).show();
    }
    public void cancel(View view) {
        connectionsClient.stopAdvertising();
        connectionsClient.stopDiscovery();
        connect.setEnabled(true);
        cancel.setVisibility(View.GONE);
    }
}
