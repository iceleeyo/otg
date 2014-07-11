package com.springtour.otg.infrastructure.channel;

import java.util.*;

import com.springtour.otg.domain.model.transaction.*;

public interface ExternalTransactionQueryObject {

	ExternalTransactionQueryResult queryBy(Transaction transaction);

	Set<String> supportedChannels();

}
