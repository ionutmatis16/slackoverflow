package ro.utcn.sd.mid.assign1.slackoverflow;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class VirtualClassroomApplication {
    public static void main(String[] args) {
        SpringApplication.run(VirtualClassroomApplication.class, args);
    }
}
