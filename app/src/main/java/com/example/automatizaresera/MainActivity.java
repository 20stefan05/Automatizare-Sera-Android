package com.example.automatizaresera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MqttCallback {
   Button addp;
   boolean fromMain = true;
   Button testButton;
    ArrayList<UsersPlantList> al;
    ListView list;
    int position;
    MqttAndroidClient client;
    boolean connected = false;
    IMqttMessageListener iMqttMessageListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Intent intentManagement = new Intent(this, PlantManagement.class);
         addp = (Button) findViewById(R.id.addplant);
        list = findViewById(R.id.list);
        testButton = findViewById(R.id.button);
         SQLiteDBHandler db = new SQLiteDBHandler(this);
        if(db.hasElement()){
            addp.setEnabled(false);
            fromMain = false;
            //for(int i = 0; i<list.getCount(); i++) list.getChildAt(i).setEnabled(false);
            int id = 0;

            al = new ArrayList<UsersPlantList>();
            al.add(new UsersPlantList(db.getPlant(id).getName(), db.getPlant(id).getImage()));
            UsersPlantListAdapter upla = new UsersPlantListAdapter(this, al);
            list.setAdapter(upla);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> list, View v, int pos, long id) {
                    //if(connected){
                    System.out.println("Click listener");
                    System.out.println(pos);//}
                }
            });
            testButton.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    System.out.println("Test button clicked");
                    position = 0;
                    startActivity(intentManagement);
                }
            });
            String clientId= MqttClient.generateClientId();
            client = new MqttAndroidClient(this, "tcp://broker.mqttdashboard.com:1883", clientId);

            try {
                client.connect().setActionCallback(new IMqttActionListener() {

                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        client.setCallback(MainActivity.this);
                        System.out.println("Mqtt connected successfully");
                        String toPublish = "1";
                        MqttMessage message = new MqttMessage(toPublish.getBytes());
                        message.setRetained(true);
                        try {
                            client.publish("CheckCon", message);
                        } catch (MqttException e) {
                            e.printStackTrace();
                        }
                        try {
                            IMqttToken subToken =  client.subscribe("CheckCon", 0);
                            subToken.setActionCallback(new IMqttActionListener() {
                                @Override
                                public void onSuccess(IMqttToken asyncActionToken) {
                                    System.out.println("Abonat cu succes");

                                }

                                @Override
                                public void onFailure(IMqttToken asyncActionToken,
                                                      Throwable exception) {
                                    System.out.println("Abonarea nu a reusit");

                                }
                            });
                        } catch (MqttException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        System.out.println("Mqtt disconnected");
                    }
                });

            } catch (MqttException e) {
                e.printStackTrace();
            }

        }
        else if(!db.hasElement()) addp.setEnabled(true);
    }
    public void addNewPlant(View view){
        //JsonCommunication jsc = new JsonCommunication();
        //jsc.execute();

        Intent intent = new Intent(this, AddPlant.class);
        startActivity(intent);
    }
    public int getPosition(){
      return this.position;
    }
    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("Mesajul a ajuns in");
        System.out.println(topic);
        System.out.println(message);
        UsersPlantListAdapter upla = new UsersPlantListAdapter(this, al);
        String str = new String(message.getPayload());
        if(topic.equals("CheckCon")&&str.equals("Confirm")) {
            System.out.println("CheckCon");
            upla.connected();
            list.setAdapter(upla);
            connected = true;
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}