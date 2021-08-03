package tacos.flow.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.transformer.GenericTransformer;

import java.io.File;

@Configuration
public class FileWriterIntegrationConfig {


    /*
     * xml configuration
     * */

    @Profile("xmlconfig")
    @Configuration
    @ImportResource("classpath:/filewriter-config.xml")
    public static class XmlConfiguration {
    }

    /*
     * Java configuration
     * */
    @Profile("javaconfig")
    // 변환기 Bean 입력
    // GenericTransformer가 inputChannel의 메시지를 받아서 outputChannel로 쓰는 통합플로우 변환기
    @Bean
    @Transformer(inputChannel = "textInChannel",
            outputChannel = "fileWriterChannel")
    public GenericTransformer<String, String> upperCaseTransformer() {
        return text -> text.toUpperCase();
    }

    @Profile("javaconfig")
    // 파일 쓰기 메시지 핸들러 bean 선언
    // inputChannel 로 부터 메시지를 받아서 FileWritingMessageHandler 인스턴스로 정의된 서비스 전달
    @Bean
    @ServiceActivator(inputChannel = "fileWriterChannel")
    public FileWritingMessageHandler fileWriter() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File("/tmp/tacos/files"));

        handler.setExpectReply(false); // 응답채널 사용 안함
        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setAppendNewLine(true);
        return handler;
    }


    /*
     * DSL Configuration
     * */
    // @Profile("javadsl")
    @Bean
    public IntegrationFlow fileWriterFlow() {
        return IntegrationFlows
                .from(MessageChannels.direct("textInChannel")) // 인바운드 채널
                .<String, String>transform(t -> t.toUpperCase()) // 변환기 transformer 선언한다다
                // .channel(MessageChannels.direct("fileWriterChannel")) // transformer를 아웃바운드 채널 어댑터와 연결하는 채널이라면 설정
                .handle(Files // 파일에 쓰는 것 처리
                        .outboundAdapter(new File("/tmp/tacos/files"))
                        .fileExistsMode(FileExistsMode.APPEND)
                        .appendNewLine(true))
                .get();
    }
}
