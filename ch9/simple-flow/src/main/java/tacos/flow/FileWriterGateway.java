package tacos.flow;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.handler.annotation.Header;

// 메시지 게이트웨이 선언
@MessagingGateway(defaultRequestChannel="textInChannel")
public interface FileWriterGateway {

    void writeToFile(
            @Header(FileHeaders.FILENAME) String filename, // 파일에 쓴다
            String data);
}
