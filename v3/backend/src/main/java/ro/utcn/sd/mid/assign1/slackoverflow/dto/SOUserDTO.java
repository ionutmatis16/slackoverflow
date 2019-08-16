package ro.utcn.sd.mid.assign1.slackoverflow.dto;

import lombok.Data;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.SOUser;


@Data
public class SOUserDTO {
    private Integer id;
    private String soUsername;
    private int score;

    public static SOUserDTO ofEntity(SOUser sOUser) {
        SOUserDTO soUserDTO = new SOUserDTO();
        soUserDTO.setId(sOUser.getId());
        soUserDTO.setSoUsername(sOUser.getSOUsername());
        soUserDTO.setScore(sOUser.getScore());
        return soUserDTO;
    }

}
