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
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
@EnableElasticsearchRepositories("com.search.demo.repository")
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
                    .put("xpack.security.user", "4q4coyezvp:h32007au1w")
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

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchTemplate(client());
    }


}