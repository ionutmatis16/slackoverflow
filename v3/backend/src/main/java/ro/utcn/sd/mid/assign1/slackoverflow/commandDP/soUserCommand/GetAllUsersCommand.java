package ro.utcn.sd.mid.assign1.slackoverflow.commandDP.soUserCommand;

import lombok.AllArgsConstructor;
import ro.utcn.sd.mid.assign1.slackoverflow.commandDP.Command;
import ro.utcn.sd.mid.assign1.slackoverflow.service.SOUserService;

@AllArgsConstructor
public class GetAllUsersCommand implements Command {
    private SOUserService userService;

    @Override
    public Object execute() {
        return userService.findAllSOUsers();
    }
}
