package com.example.a121game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.gms.nearby.connection.Strategy;

import org.json.JSONObject;

//import static java.nio.charset.StandardCharsets.UTF_8;


//class to be used for sending player info to dm
//Update DM does nothing now, but will be the button that sends data to the dm.

public class ChosenCharacter extends AppCompatActivity {
    public Intent i;


    public static final int MY_PERMISSIONS_ACCESS_COARSE_LOCATION = 0;
    private static final String TAG = "DungeonsAndDragons";
    private static final Strategy STRATEGY = Strategy.P2P_STAR;
    private ConnectionsClient connectionsClient;
    // //placeholder for player id
    private final String codeName = "simpleName";


    private Button send;
    private Button receive;

    public player myChar;

    public String role = "";

    public String jsonAsString = null;

    private String opponentEndpointId;
    private String opponentName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chosen_character);

        send = findViewById(R.id.button5);
        receive = findViewById(R.id.button6);
        i= getIntent();


        TextView playerName = (TextView) findViewById(R.id.textView22);
        TextView characterName = (TextView) findViewById(R.id.textView23);

        EditText health = (EditText) findViewById(R.id.editText3);
        EditText intellect = (EditText) findViewById(R.id.editText4);
        EditText wisdom = (EditText) findViewById(R.id.editText5);
        EditText charisma = (EditText) findViewById(R.id.editText6);
        EditText constitution = (EditText) findViewById(R.id.editText13);
        EditText strength = (EditText) findViewById(R.id.editText14);
        EditText dexterity = (EditText) findViewById(R.id.editText16);


        String pName = i.getStringExtra("PlayerName");
        String cName = i.getStringExtra("CharacterName");
        String hp = i.getStringExtra("Health");

        String intel = i.getStringExtra("intellect");
        String wis = i.getStringExtra("wisdom");
        String charis = i.getStringExtra("charisma");
        String str = i.getStringExtra("strength");
        String dex = i.getStringExtra("dexterity");
        String cons = i.getStringExtra("constitution");

        myChar = new player(pName, cName, Integer.parseInt(hp));
        myChar.stats(Integer.parseInt(intel), Integer.parseInt(wis), Integer.parseInt(charis), Integer.parseInt(str), Integer.parseInt(dex), Integer.parseInt(cons));
        JSONObject toSend = new JSONObject();
        try{
            toSend.put("PlayerName", pName);
            toSend.put("CharacterName", cName);
            toSend.put("Health", hp);
            toSend.put("intellect", intel);
            toSend.put("charisma", charis);
            toSend.put("strength", str);
            toSend.put("dexterity", dex);
            toSend.put("constitution", cons);

            jsonAsString = toSend.toString();
        }
        catch(Exception e)
        {
            Toast.makeText(ChosenCharacter.this, "problem here",
                    Toast.LENGTH_LONG).show();
        }
        playerName.setText(pName);
        characterName.setText(cName);
        health.setText(hp);

        intellect.setText(intel);
        wisdom.setText(wis);
        charisma.setText(charis);
        constitution.setText(cons);
        dexterity.setText(dex);
        strength.setText(str);
        connectionsClient = Nearby.getConnectionsClient(this);
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
                        if(role.equals("s"))
                        {
                            sendData();
                        }

                    } else {
                        Log.i(TAG, "onConnectionResult: connection failed");
                    }
                }

                @Override
                public void onDisconnected(String endpointId) {
                    Log.i(TAG, "onDisconnected: disconnected from " + endpointId);
                    role = "";
                }
            };
    private final PayloadCallback payloadCallback =
            new PayloadCallback() {
                @Override
                public void onPayloadReceived(String endpointId, Payload payload) {
                    String receivedPlayer;
                    try{
                        receivedPlayer= new String(payload.asBytes(), "utf-8");
                        JSONObject newPlayer = new JSONObject(receivedPlayer);
                    }
                    catch(Exception e)
                    {
                        Toast.makeText(ChosenCharacter.this, "Receive Byte Error.",
                                Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onPayloadTransferUpdate(String endpointId, PayloadTransferUpdate update) {
                    if((update.getStatus() == PayloadTransferUpdate.Status.SUCCESS))
                    {
                        Toast.makeText(ChosenCharacter.this, "Transfer Complete.",
                                Toast.LENGTH_LONG).show();
                    }
                    connectionsClient.disconnectFromEndpoint(endpointId);
                    send.setEnabled(true);
                    receive.setEnabled(true);
                }
            };
    public void sendData()
    {
        try
        {
            connectionsClient.sendPayload(opponentEndpointId, Payload.fromBytes(jsonAsString.getBytes("utf-8")));
        }
        catch(Exception e)
        {
            Toast.makeText(ChosenCharacter.this, "Send BYTE Error.",
                    Toast.LENGTH_LONG).show();
        }


    }
    public void send(View view)
    {
        role = "s";
        startDiscovery();
        send.setEnabled(false);
        receive.setEnabled(false);

    }
    public void receive(View view)
    {
        role = "r";
        startAdvertising();
        send.setEnabled(false);
        receive.setEnabled(false);
    }
    //will not handle failed discovery as of yet
    private void startDiscovery()
    {
        connectionsClient.startDiscovery(
                getPackageName(), endpointDiscoveryCallback, new DiscoveryOptions(STRATEGY));
        Toast.makeText(ChosenCharacter.this, "Searching... D",
                Toast.LENGTH_LONG).show();
    }
    //will not handle an advertising failure
    private void startAdvertising()
    {
        connectionsClient.startAdvertising(
                codeName, getPackageName(), connectionLifecycleCallback, new AdvertisingOptions(STRATEGY));
        Toast.makeText(ChosenCharacter.this, "Searching... A",
                Toast.LENGTH_LONG).show();
    }
    public void cancel(View view){
        role = "";
        connectionsClient.stopAdvertising();
        connectionsClient.stopDiscovery();
        send.setEnabled(true);
        receive.setEnabled(true);
    }
}
