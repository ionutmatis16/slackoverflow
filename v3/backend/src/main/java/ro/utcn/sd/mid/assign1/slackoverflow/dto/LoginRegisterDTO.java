package ro.utcn.sd.mid.assign1.slackoverflow.dto;

import lombok.Data;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.SOUser;

@Data
public class LoginRegisterDTO {
    private String soUsername;
    private String soPassword;

    public static LoginRegisterDTO ofEntity(SOUser soUser) {
        LoginRegisterDTO dto = new LoginRegisterDTO();
        dto.setSoUsername(soUser.getSOUsername());
        dto.setSoPassword(soUser.getSOPassword());

        return dto;
    }
}
