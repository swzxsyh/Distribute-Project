package com.distribute.persistence.aspect.interceptor;//package com.test.infrastructure.aspect.interceptor;
//
//import com.test.infrastructure.utils.ThreadMdcUtil;
//import org.apache.kafka.clients.producer.ProducerInterceptor;
//import org.apache.kafka.clients.producer.ProducerRecord;
//import org.apache.kafka.clients.producer.RecordMetadata;
//import org.apache.kafka.common.header.Header;
//import org.apache.kafka.common.header.internals.RecordHeader;
//import org.slf4j.MDC;
//
//import java.nio.charset.StandardCharsets;
//import java.util.Map;
//import java.util.Objects;
//
///**
// * @author tangtong
// * @date 2020/12/24
// */
//public class TraceContextProducerInterceptor implements ProducerInterceptor<String, String> {
//    @Override
//    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
//        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(record.topic(), record.partition(),
//                record.timestamp(), record.key(), record.value(), record.headers());
//        String traceId = Objects.isNull(MDC.get("traceId")) ? ThreadMdcUtil.getTraceId() : MDC.get("traceId");
//        Header header = new RecordHeader("traceId", traceId.getBytes(StandardCharsets.UTF_8));
//        producerRecord.headers().add(header);
//        return producerRecord;
//    }
//
//    @Override
//    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
//
//    }
//
//    @Override
//    public void close() {
//
//    }
//
//    @Override
//    public void configure(Map<String, ?> configs) {
//
//    }
//}
