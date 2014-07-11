package com.springtour.otg.infrastructure.security;

import lombok.*;

@Data
public class SessionUserSnapshot {
	private SimpleUserSnapshot user;
	private String branchId;
	private String branchSimpleName;
	private String deptId;
	private String deptName;
	private String marketingChannel;
}
