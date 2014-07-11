package com.springtour.otg.domain.model.transaction;

import java.util.*;

import com.springtour.otg.infrastructure.time.*;

import lombok.*;

public class CheckingBatchConfigurations {
	/**
	 * 被认为是用户正在处理中的交易的检查轮数
	 */
	@Setter
	@Getter
	public int newBornRound;
	/**
	 * 被认为是用户可能放弃的交易的检查轮数
	 */
	@Setter
	@Getter
	public int unlivelyRound;
	/**
	 * 被认为是用户已放弃的交易的检查轮数
	 */
	@Setter
	@Getter
	public int deadRound;
	/**
	 * 每轮的间隔分钟数
	 */
	@Setter
	@Getter
	public int intervalMinutes;
	@Setter
	@Getter
	public int[] unlivelyTurnRounds;

	public CheckingBatchConfigurations() {
	}

	public boolean isGreaterThanDeadRound(long round) {
		return round >= deadRound;
	}

	public boolean isLessEqualNewBornRound(long round) {
		return round <= newBornRound;
	}

	public boolean isUnlivelyAndByeRound(long round) {
		return (round >= unlivelyRound) && (round < deadRound) && bye(round);
	}

	private boolean bye(long rounds) {
		for (int candidate : unlivelyTurnRounds) {
			if (rounds == candidate) {
				return false;
			}
		}
		return true;
	}

	public long roundFor(Date whenRequested, final Date now) {
		return durationMinutesBetween(whenRequested, now) / intervalMinutes;
	}

	private long durationMinutesBetween(Date start, Date end) {
		return DateUtils.durationMillisBetween(
				DateUtils.copyWithoutSencondsAndMillis(start),
				DateUtils.copyWithoutSencondsAndMillis(end)) / 60000;
	}
}