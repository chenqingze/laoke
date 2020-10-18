package com.aihangxunxi.aitalk.im.assembler;

import com.aihangxunxi.aitalk.im.protocol.buffers.Fans;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class FansAssembler {

    public List<Fans> convertToMessage(List<com.aihangxunxi.aitalk.storage.model.Fans> fansList) {

        List<Fans> list = new ArrayList<>();
        fansList.stream().forEach(fans -> {
            Fans a = Fans.newBuilder()
                    .setFansId(fans.getUserId().toString())
                    .setNickname(fans.getNickname())
                    .setProfilePhoto(fans.getProfile_photo())
                    .build();
            list.add(a);

        });
        return list;
    }
}
