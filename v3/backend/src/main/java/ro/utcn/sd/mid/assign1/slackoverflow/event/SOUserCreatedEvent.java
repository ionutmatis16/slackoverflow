package ro.utcn.sd.mid.assign1.slackoverflow.event;


import lombok.Data;
import lombok.EqualsAndHashCode;
import ro.utcn.sd.mid.assign1.slackoverflow.dto.SOUserDTO;

@Data
@EqualsAndHashCode(callSuper = true)
public class SOUserCreatedEvent extends BaseEvent {
    private final SOUserDTO soUser;

    public SOUserCreatedEvent(SOUserDTO user) {
        super(EventType.USER_CREATED);
        this.soUser = user;
    }
}
