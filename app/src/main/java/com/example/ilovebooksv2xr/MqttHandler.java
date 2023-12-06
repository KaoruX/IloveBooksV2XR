package com.example.ilovebooksv2xr;
import android.widget.Toast;

import com.google.firebase.database.core.Context;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttHandler {

    private MqttClient client;

    public void connect(String brokerUrl, String clientId) {
        String USERNAME = "androidteststiqq";
        String PASSWORD = "W0U2XNxCKinXaOBv";
        try {
            // persistencia de datos
            MemoryPersistence persistence = new MemoryPersistence();
            MqttConnectOptions connectOptions = new MqttConnectOptions();

            connectOptions.setUserName(USERNAME);
            connectOptions.setPassword(PASSWORD.toCharArray());

            // Iniciar clietne MQTT
            client = new MqttClient(brokerUrl, clientId, persistence);



            // Opciones de conexion
            connectOptions.setCleanSession(true);

            // Conectar al broker
            client.connect(connectOptions);
        } catch (MqttException e) {
            e.printStackTrace();
        }

        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                // Manejar pérdida de conexión
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                // Este método se llama cuando se recibe un mensaje en el tema suscrito
                String messageText = new String(message.getPayload());
                // Muestra el mensaje con un Toast

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                // Este método se llama cuando se ha entregado un mensaje (por ejemplo, después de publicar un mensaje)
            }
        });

    }

    public void disconnect() {
        try {
            client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void publish(String topic, String message) {
        try {
            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            client.publish(topic, mqttMessage);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void subscribe(String topic) {
        try {
            client.subscribe(topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }



}

