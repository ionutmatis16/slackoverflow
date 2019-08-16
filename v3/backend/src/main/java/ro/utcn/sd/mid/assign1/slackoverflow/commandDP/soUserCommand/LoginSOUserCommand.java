package ro.utcn.sd.mid.assign1.slackoverflow.commandDP.soUserCommand;

import lombok.AllArgsConstructor;
import ro.utcn.sd.mid.assign1.slackoverflow.commandDP.Command;
import ro.utcn.sd.mid.assign1.slackoverflow.dto.LoginRegisterDTO;
import ro.utcn.sd.mid.assign1.slackoverflow.service.SOUserService;

@AllArgsConstructor
public class LoginSOUserCommand implements Command {
    private SOUserService userService;
    private LoginRegisterDTO dto;

    @Override
    public Object execute() {
        return userService.loginSOUser(dto);
    }
}
