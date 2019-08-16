package ro.utcn.sd.mid.assign1.slackoverflow.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.utcn.sd.mid.assign1.slackoverflow.commandDP.Invoker;
import ro.utcn.sd.mid.assign1.slackoverflow.commandDP.soUserCommand.GetAllUsersCommand;
import ro.utcn.sd.mid.assign1.slackoverflow.commandDP.soUserCommand.LoginSOUserCommand;
import ro.utcn.sd.mid.assign1.slackoverflow.commandDP.soUserCommand.RegisterSOUserCommand;
import ro.utcn.sd.mid.assign1.slackoverflow.dto.LoginRegisterDTO;
import ro.utcn.sd.mid.assign1.slackoverflow.event.BaseEvent;
import ro.utcn.sd.mid.assign1.slackoverflow.service.SOUserService;

@RestController
@RequiredArgsConstructor
public class SOUsersController {
    private final SOUserService sOUserService;
    // so we can publish to the websocket
    private final SimpMessagingTemplate messagingTemplate;
    private final Invoker invoker;

    @GetMapping("/users")
    public Object readAll() {
        invoker.setCommand(new GetAllUsersCommand(sOUserService));
        return invoker.invoke();
    }

    @PostMapping("/users")
    public Object create(@RequestBody LoginRegisterDTO dto) {
        invoker.setCommand(new RegisterSOUserCommand(sOUserService, dto));
        return invoker.invoke();
    }

    @PostMapping("/login")
    public Object readOne(@RequestBody LoginRegisterDTO dto) {
        invoker.setCommand(new LoginSOUserCommand(sOUserService, dto));
        return invoker.invoke();
    }

    @EventListener(BaseEvent.class)
    public void handleEvent(BaseEvent event) {
        // sent the event (serialized) to a topic
        messagingTemplate.convertAndSend("/topic/events", event);
    }
}
