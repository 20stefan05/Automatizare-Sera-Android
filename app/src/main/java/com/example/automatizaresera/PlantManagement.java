package com.example.automatizaresera;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class PlantManagement extends AppCompatActivity implements MqttCallback {
    TextView umiditateR, fertilitateR, luminozitateR, temperaturaR;
    //TextView baterie, umiditate, fertilitate, luminozitate, temperatura;
    ArrayList<TextView> textViews = new ArrayList<TextView>();
    String act[] = {"V", "F", "L", "H"};
    SQLiteDBHandler db;

    MainActivity ma = new MainActivity();
    Switch ajustareAuto;
    int id = ma.getPosition();
    ArrayList<Switch> actuators = new ArrayList<Switch>();
    MqttAndroidClient client;
    String baseTopic = "flora/C4:7C:8D:67:6A:23/";
    String subTopics[] = {"battery","moisture", "conductivity", "light", "temperature"};
    ArrayList<Integer> minValues = new ArrayList<Integer>();
    ArrayList<Integer> maxValues = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_management);


        umiditateR = findViewById(R.id.umiditateRecomandata);
        fertilitateR = findViewById(R.id.fertilitateRecomandata);
        temperaturaR = findViewById(R.id.temperaturaRecomandata);
        luminozitateR = findViewById(R.id.luminozitateRecomandata);
        textViews.add((TextView) findViewById(R.id.procentajBaterie));
        textViews.add((TextView) findViewById(R.id.umiditateSol));
        textViews.add((TextView) findViewById(R.id.fertilitateSol));
        textViews.add((TextView) findViewById(R.id.luminozitate));
        textViews.add((TextView) findViewById(R.id.tempertura));
        actuators.add((Switch) findViewById(R.id.valvaApa));
        actuators.add((Switch) findViewById(R.id.valvaFertilizator));
        actuators.add((Switch) findViewById(R.id.lumina));
        actuators.add((Switch) findViewById(R.id.incalzitor));

        db = new SQLiteDBHandler(this);
        SelectedPlant p = db.getPlant(id);
        umiditateR.setText(db.getPlant(id).getMinSoilMoist() + " - " + db.getPlant(id).getMaxSoilMoist());
        temperaturaR.setText(db.getPlant(id).getMinTemp() + " - " + db.getPlant(id).getMaxTemp());
        fertilitateR.setText(db.getPlant(id).getMinSoilEc() + " - " + db.getPlant(id).getMaxSoilEc());
        luminozitateR.setText(db.getPlant(id).getMinLightLux() + " - " + db.getPlant(id).getMaxLightLux());
        minValues.add(20); maxValues.add(100);
        minValues.add(db.getPlant(id).getMinSoilMoist()); maxValues.add(db.getPlant(id).getMaxSoilMoist());
        minValues.add(db.getPlant(id).getMinSoilEc()); maxValues.add(db.getPlant(id).getMaxSoilEc());
        minValues.add(db.getPlant(id).getMinLightLux()); maxValues.add(db.getPlant(id).getMaxLightLux());
        minValues.add(db.getPlant(id).getMinTemp()); maxValues.add(db.getPlant(id).getMaxTemp());
        ajustareAuto = findViewById(R.id.ajustareAutomata);
        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(this, "tcp://broker.mqttdashboard.com:1883", clientId);
        try {
            client.connect().setActionCallback(new IMqttActionListener() {

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    client.setCallback(PlantManagement.this);
                    System.out.println("Mqtt2 connected successfully");
                    final String toPublish = "1";
                    final MqttMessage message = new MqttMessage(toPublish.getBytes());
                    final MqttMessage m1 = new MqttMessage("1".getBytes());
                    final MqttMessage m0 = new MqttMessage("0".getBytes());
                    m1.setRetained(true);
                    m0.setRetained(true);
                   // for(int i = 0; i<actuators.size(); i++){
                     //   k = i;
                        ajustareAuto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if(b){
                                    for(int i = 0; i<actuators.size(); i++){
                                        //actuators.get(i).setChecked(false);
                                        //actuators.get(i).setEnabled(false);
                                        actuators.get(i).setClickable(false);
                                    }
                                    try {
                                        client.publish("AdjParams", m1);
                                        String toPublishParams = "";
                                        toPublishParams+=db.getPlant(id).getMaxLightLux(); // de verificat
                                        toPublishParams+=" ";
                                        toPublishParams+=db.getPlant(id).getMinLightLux(); // de verificat
                                        toPublishParams+=" ";
                                        toPublishParams+=db.getPlant(id).getMaxTemp(); // de verificat
                                        toPublishParams+=" ";
                                        toPublishParams+=db.getPlant(id).getMinTemp(); // de verificat
                                        toPublishParams+=" ";
                                        toPublishParams+=db.getPlant(id).getMaxEnvHumid(); // de verificat
                                        toPublishParams+=" ";
                                        toPublishParams+=db.getPlant(id).getMinEnvHumid(); // de verificat
                                        toPublishParams+=" ";
                                        toPublishParams+=db.getPlant(id).getMaxSoilMoist(); // de verificat
                                        toPublishParams+=" ";
                                        toPublishParams+=db.getPlant(id).getMinSoilMoist(); // de verificat
                                        toPublishParams+=" ";
                                        toPublishParams+=db.getPlant(id).getMaxSoilEc(); // de verificat
                                        toPublishParams+=" ";
                                        toPublishParams+=db.getPlant(id).getMinSoilEc(); // de verificat
                                        MqttMessage toPublishParamsMsg = new MqttMessage(toPublishParams.getBytes());
                                        toPublishParamsMsg.setRetained(true);
                                        client.publish("P"+id, toPublishParamsMsg);
                                    } catch (MqttException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else{
                                    for(int i = 0; i<actuators.size(); i++){
                                        //actuators.get(i).setEnabled(true);
                                        actuators.get(i).setClickable(true);
                                    }
                                    try {
                                        client.publish("AdjParams",m0);
                                    } catch (MqttException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                        actuators.get(0).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if(b) {
                                    try {
                                        client.publish(act[0]+id, m1);
                                    } catch (MqttException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else {
                                    try {
                                        client.publish(act[0]+id, m0);
                                    } catch (MqttException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                   // }
                    actuators.get(1).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if(b) {
                                try {
                                    client.publish(act[1]+id, m1);
                                } catch (MqttException e) {
                                    e.printStackTrace();
                                }
                            }
                            else {
                                try {
                                    client.publish(act[1]+id, m0);
                                } catch (MqttException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                    actuators.get(2).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if(b) {
                                try {
                                    client.publish(act[2]+id, m1);
                                } catch (MqttException e) {
                                    e.printStackTrace();
                                }
                            }
                            else {
                                try {
                                    client.publish(act[2]+id, m0);
                                } catch (MqttException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                    actuators.get(3).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if(b) {
                                try {
                                    client.publish(act[3]+id, m1);
                                } catch (MqttException e) {
                                    e.printStackTrace();
                                }
                            }
                            else {
                                try {
                                    client.publish(act[3]+id, m0);
                                } catch (MqttException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                    try {
                        client.publish("ReqData", message);
                        client.publish("CheckCon", message);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }


                    IMqttToken subToken = null;

                    try {
                        for(int i = 0; i<subTopics.length; i++){
                        subToken = client.subscribe(baseTopic+subTopics[i], 0);
                        System.out.println("Abonat la");
                        System.out.println(baseTopic+subTopics[i]);
                        }
                        for(int i = 0; i<act.length; i++) subToken = client.subscribe(act[i]+String.valueOf(id), 0);
                        client.subscribe("AdjParams", 0);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                    subToken.setActionCallback(new IMqttActionListener() {
                        @Override
                        public void onSuccess(IMqttToken asyncActionToken) {
                            System.out.println("Abonat2 cu succes");

                        }

                        @Override
                        public void onFailure(IMqttToken asyncActionToken,
                                              Throwable exception) {
                            System.out.println("Abonarea2 nu a reusit");

                        }

                    });
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        boolean b = true;

        int j = 0;
        for(int i = 0; i<subTopics.length; i++){
            if(topic.equals(baseTopic+subTopics[i])){
                j = i;
                break;
            }
            else if(i == subTopics.length-1) b = false;
        }
        if(b) {
            textViews.get(j).setText(new String(message.getPayload()));
            if (((String) textViews.get(j).getText()).contains(".")) {
                if (Float.parseFloat((String) textViews.get(j).getText()) <= maxValues.get(j)
                        && Float.parseFloat((String) textViews.get(j).getText()) >= minValues.get(j))
                    textViews.get(j).setTextColor(Color.GREEN);
                else textViews.get(j).setTextColor(Color.RED);
            } else {
                if (Integer.parseInt((String) textViews.get(j).getText()) <= maxValues.get(j)
                        && Integer.parseInt((String) textViews.get(j).getText()) >= minValues.get(j))
                    textViews.get(j).setTextColor(Color.GREEN);
                else textViews.get(j).setTextColor(Color.RED);
            }
        }
        else if (topic.charAt(0)=='V' || topic.charAt(0)=='F' || topic.charAt(0)=='L' || topic.charAt(0)=='H'){
            if(new String(message.getPayload()).equals("1"))
            actuators.get(Arrays.asList(act).indexOf(Character.toString(topic.charAt(0)))).setChecked(true);
            else if(new String(message.getPayload()).equals("0"))
                actuators.get(Arrays.asList(act).indexOf(Character.toString(topic.charAt(0)))).setChecked(false);
        }
        else if(topic.equals("AdjParams")){
            if(new String(message.getPayload()).equals("1")) ajustareAuto.setChecked(true);
            else if(new String(message.getPayload()).equals("0")) ajustareAuto.setChecked(false);
        }

    }
    public void changePlant(View v){
        Intent intent = new Intent(this, AddPlant.class);
        startActivity(intent);
    }
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}