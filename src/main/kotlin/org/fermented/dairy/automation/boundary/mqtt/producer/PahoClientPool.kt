package org.fermented.dairy.automation.boundary.mqtt.producer

import io.github.oshai.kotlinlogging.KotlinLogging
import io.micronaut.mqtt.v5.config.MqttClientConfigurationProperties
import jakarta.inject.Singleton
import org.apache.commons.pool2.BasePooledObjectFactory
import org.apache.commons.pool2.PooledObject
import org.apache.commons.pool2.impl.DefaultPooledObject
import org.apache.commons.pool2.impl.GenericObjectPool
import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import org.eclipse.paho.mqttv5.client.MqttClient
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions
import java.time.Duration
import java.util.concurrent.atomic.AtomicInteger


@Singleton
class PahoClientPool(private val mqttConfig: MqttClientConfigurationProperties) {

    private var pool: GenericObjectPool<MqttClient> = GenericObjectPool(
        MqttPahoV5ClientFactory(
            serverUri = mqttConfig.serverUri,
            clientId = mqttConfig.clientId,
            connectionTimeout = mqttConfig.connectionTimeout
        ),
        getGenericObjectPoolConfig(),
    )

    fun borrowObject() = PooledMqttClient(pool)

    companion object {

        fun getGenericObjectPoolConfig(): GenericObjectPoolConfig<MqttClient?> {
            val config = GenericObjectPoolConfig<MqttClient?>()
            config.maxTotal = 5
            config.minIdle = 1
            config.maxIdle = 1
            return config
        }
    }
}

private class MqttPahoV5ClientFactory(
    val serverUri: String,
    val clientId: String,
    val connectionTimeout: Duration
) : BasePooledObjectFactory<MqttClient>() {

    var connectionCount: AtomicInteger = AtomicInteger(0)

    override fun create(): MqttClient {
        val client = MqttClient(serverUri, clientId)
        val connOpts = MqttConnectionOptions()
        connOpts.connectionTimeout = connectionTimeout.toSeconds().toInt()
        println("Connecting to broker: $serverUri")
        //client.connect()
        val conCount = connectionCount.incrementAndGet()
        logger.debug { "create: Connection Count: $conCount" }
        return client
    }

    override fun wrap(mqttClient: MqttClient?): PooledObject<MqttClient> = DefaultPooledObject(mqttClient)

    override fun destroyObject(pooledObject: PooledObject<MqttClient>) {
        //pooledObject.getObject().disconnect()
        val conCount = connectionCount.decrementAndGet()
        logger.debug { "destroyObject: Connection Count: $conCount" }
    }


    companion object {

        private val logger = KotlinLogging.logger(MqttPahoV5ClientFactory::class.java.canonicalName)
    }
}

interface Pooled<T> : AutoCloseable {
    fun borrow(): T
}

class PooledMqttClient(private val clientPool: GenericObjectPool<MqttClient>) : Pooled<MqttClient> {

    private val mqttClient: MqttClient = clientPool.borrowObject()!!

    override fun borrow(): MqttClient {
        return mqttClient
    }

    override fun close() {
        mqttClient.disconnect()
        clientPool.returnObject(mqttClient)
    }


}
