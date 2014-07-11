package com.springtour.otg.infrastructure.logging;

import org.apache.log4j.*;

import com.springtour.otg.application.impl.*;
import com.springtour.otg.application.util.*;
import com.springtour.otg.domain.model.transaction.*;

public class SynchronousCheckingBatchNotifierImpl implements
		CheckingBatchNotifier {

	protected Logger logger() {
		return LoggerFactory.getLogger();
	}

	@Override
	public void notifyError(Transaction transaction, Exception e) {

		logger().error(describe(transaction) + ",error=" + e.getMessage(), e);
	}

	private String describe(Transaction transaction) {
		return Constants.EMPTY + transaction.getTransactionNo()
				+ Constants.COMMA + "state=" + transaction.state();
	}

	@Override
	public void notifySpottingDead(Transaction transaction) {
		logger().info(describe(transaction) + " is regardes as dead.");
	}

	@Override
	public void notifySpottingNewBorn(Transaction transaction) {
		logger().info(describe(transaction) + " is regardes as new Born.");
	}

	@Override
	public void notifySpottingUnlivelyAndBye(Transaction transaction) {
		logger().info(describe(transaction) + " is regardes as unlively and bye round.");
	}

	@Override
	public void notifyToBeChecked(Transaction transaction) {
		logger().info(describe(transaction) + " is regardes as to be checked.");
	}

}
