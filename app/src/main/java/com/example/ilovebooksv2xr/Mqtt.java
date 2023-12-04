package com.example.ilovebooksv2xr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaCodec;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Mqtt extends AppCompatActivity {

    FloatingActionButton fab;
    private static final String BROKER_URL = "mqtt://androidteststiqq.cloud.shiftr.io:1883";
    private static final String CLIENT_ID = "1883";
    private MqttHandler mqttHandler;
    private EditText editTextMensaje;
    private TextView textViewMostrarMensaje;
    private Button enviarMensaje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mqtt);
        mqttHandler = new MqttHandler();
        mqttHandler.connect(BROKER_URL,CLIENT_ID);

        fab = findViewById(R.id.fab);
        editTextMensaje = findViewById(R.id.message);
        textViewMostrarMensaje = findViewById(R.id.textViewMessage);
        enviarMensaje = findViewById(R.id.enviarButton);

        enviarMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewMostrarMensaje.setText(editTextMensaje.getText().toString());
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Mqtt.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //Preguntar a profesor si esta bien colocado.

        mqttClient.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                // Manejar pérdida de conexión
            }
            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                // Este método se llama cuando se recibe un mensaje en el tema suscrito
                String messageText = new String(message.getPayload());
                // Muestra el mensaje con un Toast
                Toast.makeText(Mqtt.this, messageText, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                // Este método se llama cuando se ha entregado un mensaje (por ejemplo, después de publicar un mensaje)
            }
        });


    }

    @Override
    protected void onDestroy() {
        mqttHandler.disconnect();
        super.onDestroy();

    }
    private void publishMessage(String topic, String message){
        Toast.makeText(this, "Publishing message: " + message, Toast.LENGTH_SHORT).show();
        mqttHandler.publish(topic,message);
    }
    private void subscribeToTopic(String topic){
        Toast.makeText(this, "Subscribing to topic "+ topic, Toast.LENGTH_SHORT).show();
        mqttHandler.subscribe(topic);
    }



}