package com.springtour.otg.application.impl;

import com.springtour.otg.domain.model.transaction.*;

public interface CheckingBatchNotifier {

	void notifyError(Transaction transaction, Exception e);

	void notifySpottingDead(Transaction transaction);

	void notifySpottingNewBorn(Transaction transaction);

	void notifySpottingUnlivelyAndBye(Transaction transaction);

	void notifyToBeChecked(Transaction transaction);

}
