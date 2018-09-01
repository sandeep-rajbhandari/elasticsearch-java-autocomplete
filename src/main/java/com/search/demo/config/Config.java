package com.search.demo.config;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
@ComponentScan(basePackages = { "com.search.demo" })
public class Config {

    @Value("${elasticsearch.cluster.name:elasticsearch}")
    private String clusterName;

    @Value("${elasticsearch.cluster.address:127.0.0.1}")
    private String clusterAddress;

    @Value("${elasticsearch.cluster.port:9300}")
    private int clusterPort;

    @Bean(destroyMethod = "close")
    public Client client() {
        TransportClient client = null;
        try {
            final Settings elasticsearchSettings = Settings.builder()
                    .put("cluster.name", clusterName)
                    .put("client.transport.sniff", true)
                    .build();

            client = new PreBuiltXPackTransportClient(elasticsearchSettings);

            client.addTransportAddress(new InetSocketTransportAddress(
                    InetAddress.getByName(clusterAddress), clusterPort));

        } catch (UnknownHostException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        return client;
    }


}