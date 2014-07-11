package com.springtour.otg.domain.model.channel;

import java.util.*;

public interface ChannelRepository {

    Channel find(String channelId);

    List<Channel> listAll();

    void store(Channel channel);
}
